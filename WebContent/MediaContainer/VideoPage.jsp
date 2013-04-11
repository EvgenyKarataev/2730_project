<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- Model is VideoPageModel -->    
<video width="${videoPageModel.width}" height="${videoPageModel.height}" preload="auto" controls>
        <source src="/2730_project/UserFiles/User_<%=request.getSession().getAttribute("userId") %>/${videoPageModel.videoFilename}" type="video/${videoPageModel.extension}" />
        Your browser does not support the video tag.
</video>