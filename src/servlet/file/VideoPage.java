package servlet.file;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.VideoPageModel;

@WebServlet("/MediaContainer/VideoPage")
public class VideoPage extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String videoFilename = request.getParameter("videoFilename");
		int width = 420;
		int height = 270;
		
		VideoPageModel model = new VideoPageModel();
		model.setVideoFilename(videoFilename);
		model.setHeight(height);
		model.setWidth(width);
		
		request.setAttribute("videoPageModel", model);		
		request.getRequestDispatcher("/MediaContainer/VideoPage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
