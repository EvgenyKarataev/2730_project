<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

@model List<SALT.MVC.Models.FileListModel>
@{ int userId = Convert.ToInt32(HttpContext.Current.Session["userId"]); }

<div id="mediaListViewContainer">
    <ul id="mcFileList">
        @foreach (var fileModel in Model)
        {
            string fileType = "";   
            switch (fileModel.FileType)
            {
                case FileListModel.FILE_TYPE_IMAGE:
                    fileType = "mcFileType_image";
                    break;
                case FileListModel.FILE_TYPE_VIDEO:
                    fileType = "mcFileType_video";
                    break;
                default:
                    fileType = "mcFileType_other";
                break;
            }                                
            <li class="mcFileListItem @fileType">
                @if (fileModel.FileType == FileListModel.FILE_TYPE_IMAGE)
                {
                    <i class="icon-picture" class="mcFileListIcon"></i>
                }
                else if (fileModel.FileType == FileListModel.FILE_TYPE_VIDEO)
                {
                    <i class="icon-film" class="mcFileListIcon"></i>
                }
                else
                {
                    <i class="icon-file" class="mcFileListIcon"></i>
                }
                <input type="hidden" class="fileSrc" value="~/UserFiles/User_@userId/@fileModel.Filename"/>
                <div class="filename_wrapper">
                    <span class="mcFileListText">@fileModel.Filename</span>
                    <i class="icon-remove hidden"></i>
                </div>
            </li>
        }
    </ul>
</div>
<div id="mediaGalleryViewContainer" class="hidden">
    <ul class="thumbnails">
        @foreach (var fileModel in Model)
        {
            string fileType = fileModel.FileType == FileListModel.FILE_TYPE_IMAGE ? "mcFileType_image" : "mcFileType_video";
            <div class="span6 mcImageWrapper">
                <li class="@fileType" title="@fileModel.Filename">
                    <a class="thumbnail">
                        @if (fileModel.FileType == FileListModel.FILE_TYPE_IMAGE)
                        {
                             <img src="~/UserFiles/User_@userId/@fileModel.Filename" title="@fileModel.Filename" alt="" />
                        }
                        else
                        {
                             <img src="~/Uploads/FileContainer_icons/video-gallery.png" />
                        }                      
                    </a>
                    <input type="hidden" class="fileSrc" value="~/UserFiles/User_@userId/@fileModel.Filename"/>
                </li>
            </div>                                        
        }
    </ul>
</div>
