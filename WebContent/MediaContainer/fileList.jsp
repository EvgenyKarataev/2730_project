<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.*" %>

<!DOCTYPE html>

<!-- Model ArrayList<model.FileListModel>  -->
<%  int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString()); %>
<%  List<FileListModel> fileList = (List<FileListModel>)request.getAttribute("fileList"); %>

<div id="mediaListViewContainer">
    <ul id="mcFileList">
        <% for(FileListModel fileModel : fileList){ 
            String fileType = "";   
            switch (fileModel.getFileType())
            {
                case FileListModel.FILE_TYPE_IMAGE:
                    fileType = "mcFileType_image";
                    break;
                case FileListModel.FILE_TYPE_VIDEO:
                    fileType = "mcFileType_video";
                    break;
                case FileListModel.FILE_TYPE_CODE:
                    fileType = "mcFileType_video";
                    break;
                default:
                    fileType = "mcFileType_other";
                break;
            }  
        %>
            <li class="mcFileListItem <%=fileType%>">
                <% if (fileModel.getFileType() == FileListModel.FILE_TYPE_IMAGE) {%>
                
                    <i class="icon-picture" class="mcFileListIcon"></i>
                <% } else if (fileModel.getFileType() == FileListModel.FILE_TYPE_VIDEO) { %>
                
                    <i class="icon-film" class="mcFileListIcon"></i>
                <% } else { %>
                
                    <i class="icon-file" class="mcFileListIcon"></i>
                <% } %>
                <input type="hidden" class="fileSrc" value="<%="UserFiles/"+ userId +"/" + fileModel.getFilename() %>" />
                <div class="filename_wrapper">
                    <span class="mcFileListText"><%=fileModel.getFilename()%></span>
                    <i class="icon-share-alt hidden" title="Share this file"></i>
                    <i class="icon-remove hidden" title="Delete this file"></i>
                </div>
            </li>
        <% } %>
    </ul>
</div>
<div id="mediaGalleryViewContainer" class="hidden">
    <ul class="thumbnails">
        <% for(FileListModel fileModel : fileList){ 
        	String fileType = fileModel.getFileType() == FileListModel.FILE_TYPE_IMAGE ? "mcFileType_image" : "mcFileType_video";          
        %>       
            <div class="span6 mcImageWrapper">
                <li class="<%=fileType%>" title="<%=fileModel.getFilename()%>">
                    <a class="thumbnail">
                        <% if (fileModel.getFileType() == FileListModel.FILE_TYPE_IMAGE){ %>
                             <img src="<%="UserFiles/"+ userId +"/"+fileModel.getFilename() %>" title="<%=fileModel.getFilename() %>" alt="" />
                        <% }else if(fileModel.getFileType() == FileListModel.FILE_TYPE_VIDEO){ %>
                             <img src="img/FileContainer_icons/video-gallery.png" />
                        <% }else { %>
                        	 <img src="img/FileContainer_icons/text-file-icon.png" />
                        <% } %>                    
                    </a>
                    <input type="hidden" class="fileSrc" value="<%="UserFiles/"+ userId +"/"+fileModel.getFilename() %>"/>
                </li>
            </div>                                        
        <% } %>
    </ul>
</div>
