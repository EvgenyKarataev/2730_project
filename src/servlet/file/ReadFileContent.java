package servlet.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import dao.FileDAO;

@WebServlet("/MediaContainer/ReadFileContent")
public class ReadFileContent extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getSession().getAttribute("userId") == null)
			return;

		int userId = Integer.parseInt(request.getSession()
				.getAttribute("userId").toString());
		String filename = request.getParameter("filename");

		boolean isSuccess = true;
		
		String targetFileStr = "";
		try {
				
			BufferedReader reader = new BufferedReader( new FileReader (request.getServletContext().getRealPath("/")
					+ "/"+ filename));
			
		    String         line = null;
		    StringBuilder  stringBuilder = new StringBuilder();
		    String         ls = System.getProperty("line.separator");

		    while( ( line = reader.readLine() ) != null ) {
		        stringBuilder.append( line );
		        stringBuilder.append( ls );
		    }

		    targetFileStr = stringBuilder.toString();
			
			
		} catch (Exception e) {
			isSuccess = false;
			targetFileStr = e.getMessage();
		}

		JSONObject json = new JSONObject();
		try {
			json.put("isSuccess", isSuccess);
			json.put("fileContent", targetFileStr);
		} catch (Exception e) {
		}
		
		response.getWriter().print(json);
		
	}
}
