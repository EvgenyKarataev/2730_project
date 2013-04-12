<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	boolean isLogin = request.getSession().getAttribute("userId") != null;
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- Add context path to every links, do not need / as prefix -->
<base href="${pageContext.request.contextPath}/" />

<title>Code Editor</title>

<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap-lightbox.min.css" />
<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="codemirror/lib/codemirror.css" />
<link rel="stylesheet" type="text/css" href="css/2730_project/CodeEditor.css" />
<link rel="stylesheet" type="text/css" href="css/2730_project/mediaContainer.css" />

<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-lightbox.js"></script>

<!-- Codemirror's lib -->
<script src="codemirror/lib/codemirror.js"></script>
<script src="codemirror/lib/formatting.js"></script>
<script src="codemirror/mode/clike/clike.js"></script>
<script src="codemirror/mode/javascript/javascript.js"></script>
<script src="codemirror/addon/fold/indent-fold.js"></script>

<script src="js/2730_project/global.js"></script>
<script src="js/2730_project/mediaContainer.js"></script>
<script src="js/2730_project/CodeEditor.js"></script>

</head>
<body>
	<input type="hidden" id="userIdInput" value="<%=request.getSession().getAttribute("userId") %>" />
	<div class="row" id="header">
		<div class="controlBtn" id="logoContainer"></div>

		<div id="controlBtns">
			<div class="controlBtn" id="undoBtn">
				<i class="icon-undo"></i> <span class="btnText">Undo</span>
			</div>
			<div class="controlBtn" id="redoBtn">
				<i class="icon-repeat"></i> <span class="btnText">Redo</span>
			</div>
			<div class="controlBtn" id="formatBtn">
				<i class="icon-list"></i> <span class="btnText">Format</span>
			</div>
			<div class="controlBtn" id="runBtn">
				<i class="icon-play-circle"></i> <span class="btnText">Run</span>
			</div>
			<div class="controlBtn" id="saveBtn">
				<i class="icon-save"></i> <span class="btnText">Save</span>
			</div>			
			<div class="absolutePanel hidden" id="savePanel">
			<% if(isLogin){ %>
				<div style="margin: 5px 0 0 1px;font-size: 11px;">Filename:</div>
				<input type="text" id="filenameInput" name="filename"/>
				<input type="hidden" id="isNewFileInput" name="isNewFile" value="true"/>
				<div id="saveFileBtns">
					<i class="icon-save" title="Save"></i>
					<i class="icon-remove-circle" title="Cancel"></i>				
				</div>
				<%}else{ %>
					<div style="margin: 5px 0 0 1px;font-size: 11px;height: 30px;">Please login to use this function.</div>
				<% } %>
			</div>			
		</div>
		
		<% 
			String title = isLogin? "Logout" : "";
			// Link of login/logout button.
			String link = isLogin? "Logout" : "Login";
		%>
		<div class="controlBtn" id="loginContainer" title="<%=title%>">
			<a href="<%=link%>">
			<%
				if (isLogin) {
			%>
			<span class="btnText" id="userNameText">
			<%=request.getSession().getAttribute("userName") %>
			</span>
			<i class="icon-signout" title="Logout"></i> 
			<%
				} else {
			%>
			<i class="icon-signin"></i> <span class="btnText">Login/Signup</span>
			<%
				}
			%>
			</a>
		</div>
	</div>

	<div class="row" id="content">
		<div class="span2 contentPane" id="leftCP">
			<div id="controlPanel">
				<div id="languagePanel">
					<span id="languageText">Language</span> <select id="languageSelect">
						<option value="Java">Java SE 1.7</option>
						<option value="Python">Python 3.3</option>
					</select>
				</div>
			</div>
			<div id="fileContainerWrapper"></div>
		</div>
		<div class="span6 contentPane" id="middleCP"></div>
		<div class="span4 contentPane" id="rightCP">
			<div id="dragBoard">
				<p id="dragBoardText">Drag Board</p>
			</div>
			<div id="outputPanelWrapper">
				<div id="outputPanel">			
				</div>
			</div>
		</div>
	</div>

<!-- lightboxes -->
<div id="saltLightBox" class="lightbox hide fade" tabindex="-1" role="dialog" aria-hidden="true">
    <div class='lightbox-header'>
        <button type="button" class="close" data-dismiss="lightbox" aria-hidden="true">&times;</button>
    </div>
    <div class='lightbox-content'>
        <!--<div class="lightbox-caption"><p>Your caption here</p></div>-->
        <img src="" />
        <video controls>
            <source src="movie.mp4" type="video/mp4">
            Your browser does not support the video tag.
        </video>
    </div>
</div>
<div id="fileUploadLightBox" class="lightbox hide fade" tabindex="-1" role="dialog" aria-hidden="true">
    <div class='lightbox-header'>
        <button type="button" class="close" data-dismiss="lightbox" aria-hidden="true">&times;</button>
    </div>
    <div class='lightbox-content'>
       <!--<div class="lightbox-caption"><p>Your caption here</p></div>-->
    </div>
</div>

</body>
</html>