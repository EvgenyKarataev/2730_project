/// <reference path="../tiny_mce/editorEmbedded.js" />
var mediaContainer = (function () {
    var mediaContainer = {};

    // Once the dom of media container loaded, class this function.
    mediaContainer.initComponents = function () {

        mediaContainer.outerContainer = $('#mediaContainerWrapper');
        mediaContainer.toolsBtn = $(mediaContainer.outerContainer).find('#mediaToolsBtn');
        mediaContainer.listViewBtn = $(mediaContainer.outerContainer).find('#mcListViewBtn');
        mediaContainer.galleryViewBtn = $(mediaContainer.outerContainer).find('#mcGalleryViewBtn');
        mediaContainer.imageFilterBtn = $(mediaContainer.outerContainer).find('#mcImageFilterBtn');
        mediaContainer.videoFilterBtn = $(mediaContainer.outerContainer).find('#mcVideoFilterBtn');
        mediaContainer.resizeBanner = $(mediaContainer.outerContainer).find('#mcResizeBanner');
        mediaContainer.mediaContainer = $(mediaContainer.outerContainer).find('#mediaContainer');
        mediaContainer.fileListContainer = $(mediaContainer.outerContainer).find('#fileListContainer');

        // Load file list.
        loadFiles();

        // Bind component click event.
        // $(mediaContainer.toolsBtn).click(toolsBtnClicked);
        $(mediaContainer.listViewBtn).click(listViewBtnClicked);
        $(mediaContainer.galleryViewBtn).click(galleryViewBtnClicked);
        $(mediaContainer.imageFilterBtn).click(function () {
            $(this).toggleClass('active');
            filterImage();
        });
        $(mediaContainer.videoFilterBtn).click(function () {
            $(this).toggleClass('active');
            filterVideo();
        });
        $(mediaContainer.resizeBanner).click(resizeBannerClicked);

        // Search bar key event.
        mediaContainer.searchInput = $(mediaContainer.outerContainer).find('#mediaSearchInput');
        $(mediaContainer.searchInput).keyup(function () {
            searchMediaContainer(this);
        });

        // Tools menu item click event.
        $(mediaContainer.outerContainer).find('#fileMenuItem').click(fileMenuItemClicked);

        // When upload page is hidden, reload file list.
        $(masterPage.fileUploadLightbox).bind('hidden', loadFiles);
        
        // Tools menu item click event.
        $(mediaContainer.outerContainer).find('#fileMenuItemDraw').click(fileMenuItemDrawClicked);
    };

    /* Functions */

    mediaContainer.loadMediaContainer = function (container) {
        $.post(appRootPath + '/MediaContainer/Index',
            function (data) {
                $(container).append(data);
                mediaContainer.initComponents();
            },
        'html');
    };

    // Handle drop event of tyniMCE editor.
    mediaContainer.fileItemDropEventHandler = function (ev) {

        // If the source is from the media container, get fileSrc from dragstart event.
        // Otherwise call default drag n drop event.
        if ($.inArray('filesrc', ev.dataTransfer.types) >= 0) {
            ev.preventDefault();
        }

        var fileSrc = ev.dataTransfer.getData("fileSrc");
        var fileExt = fileSrc.substring(fileSrc.lastIndexOf('.') + 1, fileSrc.length);
        var fileType = getFileType(fileSrc);
        var appendedDom;

        if (fileType == 'image') {
            // Append image to editor.
            appendedDom = $('<img src="' + fileSrc + '" class="lessletContentImage" height="100" width="100" />');
        } else if (fileType == 'video') {

            // Append video to editor.
            // May be useful if something can only be done in independent web page.
            /*
            var videoFilename = fileSrc.substring(fileSrc.lastIndexOf('/') + 1, fileSrc.length);          
            appendedDom = $('<iframe id="editorVideoIFrame" ' +
                'src="' + appRootPath + '/MediaContainer/VideoPage/' + videoFilename + '/?width=280&height=180" ' +
                'style="width: 300px; height: 200px;"' +
                'data-mce-selected="1"></iframe>');
             */

            appendedDom = $(
                '<video preload="true" style="border: solid 1px;" controls="controls">' +
                '<source src="' + fileSrc + '" type="video/' + fileExt + '">' +
                'Your browser does not support the video tag.' +
                '</video>');

        } else {
            console.log('Invalid drag/drop');
        }

        ev.target.appendChild($(appendedDom).get()[0]);
        setTimeout(function () {
            $(ev.target).find('.mceItemVideo').die().live('dblclick', function (e) {
                mediaContainer.previewVideo(fileSrc);
            });
        }, 500);
    };

    mediaContainer.previewVideo = function (fileSrc) {
        var videoFilename = fileSrc.substring(fileSrc.lastIndexOf('/') + 1, fileSrc.length);
        showIFrameLightBox(appRootPath + '/MediaContainer/VideoPage/' + videoFilename, 440, 290);
    };

    /* *********** */

    function loadFiles() {
        $(mediaContainer.fileListContainer).html('<img src="' + appRootPath + '/Images/ajax-loader-wt.gif"/>');

        $.post(appRootPath + '/MediaContainer/FileList', function (data) {
            $(mediaContainer.fileListContainer).html(data);
            initFileListComponents();
        }, 'html');
    }

    function initFileListComponents() {
        mediaContainer.listViewContainer = $(mediaContainer.outerContainer).find('#mediaListViewContainer');
        mediaContainer.galleryViewContainer = $(mediaContainer.outerContainer).find('#mediaGalleryViewContainer');

        // Set view and filter based on current state.
        if ($(mediaContainer.listViewBtn).hasClass('active')) {
            listViewBtnClicked();
        } else {
            galleryViewBtnClicked();
        }
        filterImage();
        filterVideo();

        // Bind file list item click and hover event.
        $(mediaContainer.listViewContainer).find('.icon-picture').add('.icon-film').click(fileItemClicked);

        // Show / Hide remove icon.
        $(mediaContainer.listViewContainer).find('li')
            .hover(function () {
                $(this).find('.icon-remove').removeClass('hidden');
            }).mouseleave(function () {
                $(this).find('.icon-remove').addClass('hidden');
            });

        // Delete file from file list.
        $(mediaContainer.listViewContainer).find('.icon-remove').click(function () {
            var removeIconDom = this;
            var filename = $(removeIconDom).siblings('.mcFileListText').text();

            // Prompt user of deletion.
            if (confirm('Delete "' + filename + '" ?')) {
                $.post(appRootPath + '/MediaContainer/DeleteFile',
                    { filename: filename },
                    function (data) {
                        // Remove from file list view.
                        $(removeIconDom).parentsUntil('li').parent().remove();
                        
                        // Remove from file gallery view.
                        $('#mediaGalleryViewContainer').find('img[title="' + filename + '"]:eq(0)').parentsUntil('li').parent().remove();
                    }
                );
            }
        });

        // Bind thumbnail click event.
        $(mediaContainer.galleryViewContainer).find('.thumbnail').click(fileItemClicked);

        // Bind item drag event.
        $(mediaContainer.fileListContainer).find('li').attr('draggable', 'true').each(function (index, fileItemDom) {
            fileItemDom.addEventListener('dragstart', function (e) {
                e.dataTransfer.effectAllowed = 'move';
                e.dataTransfer.setData('fileSrc', $(this).find('.fileSrc').val());
            },
            false);
        });
    }

    function resizeBannerClicked() {
        // Show / hide media container.
        $(mediaContainer.mediaContainer).toggleClass('hidden');
    }

    // Show file upload page.
    function fileMenuItemClicked() {
        showIFrameLightBox(appRootPath + '/MediaContainer/FileUploader', $(window).width() * 0.8, $(window).height() * 0.8);
    }

    function fileMenuItemDrawClicked() {
        var lightboxContentDom = $(masterPage.lightbox).find('.lightbox-content');

        // Show file manager in lightbox.
        $(lightboxContentDom).html($(masterPage.loadingIconDom).clone());
        $(masterPage.lightbox).lightbox({
            resizeToFit: false
        });

        $.post(appRootPath + '/MediaContainer/Draw', function (data) {
            $(lightboxContentDom).html(data);
            $(masterPage.lightbox).lightbox({
                resizeToFit: false
            });
        }, 'html');
    }

    function listViewBtnClicked() {
        // Change btn style.
        $(this).parent().children('button').removeClass('active');
        $(this).addClass('active');

        // Change to list view.
        $(mediaContainer.galleryViewContainer).addClass('hidden');
        $(mediaContainer.listViewContainer).removeClass('hidden');

        // Filter list view.
        searchMediaContainer();
    }

    function galleryViewBtnClicked() {
        // Change btn style.
        $(this).parent().children('button').removeClass('active');
        $(this).addClass('active');

        // Change to Gallery view.
        $(mediaContainer.listViewContainer).addClass('hidden');
        $(mediaContainer.galleryViewContainer).removeClass('hidden');

        // Filter gallery view.
        searchMediaContainer();
    }

    function filterImage() {
        if ($(mediaContainer.imageFilterBtn).hasClass('active')) {
            // Show images
            $(mediaContainer.listViewContainer).add(mediaContainer.galleryViewContainer).find('.mcFileType_image').removeClass('hidden');
        } else {
            // hide images
            $(mediaContainer.listViewContainer).add(mediaContainer.galleryViewContainer).find('.mcFileType_image').addClass('hidden');
        }
    }

    function filterVideo() {
        if ($(mediaContainer.videoFilterBtn).hasClass('active')) {
            // Show videos
            $(mediaContainer.listViewContainer).add(mediaContainer.galleryViewContainer).find('.mcFileType_video').removeClass('hidden');
        } else {
            // hide videos
            $(mediaContainer.listViewContainer).add(mediaContainer.galleryViewContainer).find('.mcFileType_video').addClass('hidden');
        }
    }

    // Move image or video to lightbox.
    function fileItemClicked() {
        var fileSrc = $(this).siblings('.fileSrc').val();
        var fileType = getFileType(fileSrc);
        if (fileType == 'image') {
            // Image
            showFileInLightbox(fileSrc, fileType);
        } else if (fileType == 'video') {
            // Video
            var videoFilename = fileSrc.substring(fileSrc.lastIndexOf('/') + 1, fileSrc.length);
            showIFrameLightBox(appRootPath + '/MediaContainer/VideoPage/' + videoFilename, 440, 290);
        }
    }

    // Used by fileListItemClicked and thumbnailClicked.
    // Only for image.
    function showFileInLightbox(src) {
        var lightboxContentDom = $(masterPage.lightbox).find('.lightbox-content');
        var imgDom = $('<img src="' + src + '" />');
        $(lightboxContentDom).html(imgDom);
        $(masterPage.lightbox).lightbox();
    }

    // Show lightbox for iframe.
    // Including file upload page and video page.
    function showIFrameLightBox(src, width, height) {
        var lightboxContentDom = $(masterPage.fileUploadLightbox).find('.lightbox-content');

        // Show file manager in lightbox.      
        var iframe = $('<iframe id="fileUploaderIFrame" src="' + src + '/"/>');

        // Set iframe style.
        if (width && height) {
            $(iframe).css({ width: width, height: height });
        }

        $(lightboxContentDom).html(iframe);
        $(masterPage.fileUploadLightbox).lightbox({
            resizeToFit: false
        });
    }

    // Filter gallery and list view.
    function searchMediaContainer() {
        var keyword = $(mediaContainer.searchInput).val();

        if ($(mediaContainer.listViewBtn).hasClass('active')) {
            // Search list view.
            searchListView(keyword);
        } else {
            // Search gallery view.
            searchGalleryView(keyword);
        }
    }

    // Filter file list.
    function searchListView(keyword) {
        var filteredList = $(mediaContainer.listViewContainer).children('#mcFileList').children('li');

        // Show all content.
        $(filteredList).removeClass('hidden');

        // Keyword must have value.
        if ($.trim(keyword) == "") return;

        $(filteredList).filter(function (index) {
            var result = $(this).find('.mcFileListText').text().indexOf(keyword) >= 0;
            return !result;
        }).addClass('hidden');
    }

    // Filter gallery
    function searchGalleryView(keyword) {
        var filteredList = $(mediaContainer.galleryViewContainer).children('.thumbnails').children('.mcImageWrapper');

        // Show all content.
        $(filteredList).removeClass('hidden');

        // Keyword must have value.
        if ($.trim(keyword) == "") return;

        $(filteredList).filter(function (index) {
            var result = $(this).attr('title').indexOf(keyword) >= 0;
            return !result;
        }).addClass('hidden');
    }

    // return 'image' / 'video' / 'other'.
    function getFileType(fileSrc) {
        var ext = fileSrc.substring(fileSrc.lastIndexOf('.') + 1, fileSrc.length);
        if (/(jpg|png|jpeg|tiff|gif|bmp)/gi.test(ext)) return 'image';
        else if (/(mp4|ogg)/gi.test(ext)) return 'video';
        else return 'other';
    }

    // Load media container (MC) when lesslet editor is opened.
    // Hide MC when lesslet editor is closed.
    $(EventManager).bind('addLessletPageLoaded editLessletPageLoaded', function () {
        // Load MC.
        mediaContainer.loadMediaContainer(masterPage.mainCP);
    });

    return mediaContainer;
})();