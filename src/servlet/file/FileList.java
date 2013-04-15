package servlet.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.FileListModel;

@WebServlet("/MediaContainer/FileList")
public class FileList extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getSession().getAttribute("userId") == null) return;
		
		File dir = new File(request.getServletContext().getRealPath(
				"/UserFiles/" + request.getSession().getAttribute("userId")));
		
		List<FileListModel> userFiles = new ArrayList<FileListModel>();
		File[] fileList = dir.listFiles();	
		for(File file : fileList){
			FileListModel model = new FileListModel();
			model.setFilename(file.getName());
			userFiles.add(model);
		}
		
		request.setAttribute("fileList", userFiles);
		request.getRequestDispatcher("/MediaContainer/fileList.jsp").forward(request, response);
	}

}
