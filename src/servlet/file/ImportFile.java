package servlet.file;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import dao.FileDAO;

/**
 * Servlet implementation class ImportFile
 */
@WebServlet("/MediaContainer/ImportFile")
public class ImportFile extends HttpServlet {
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("userId") == null)
			return;

		int userId = Integer.parseInt(request.getSession()
				.getAttribute("userId").toString());
		String fileLink = request.getParameter("fileLink");

		// Get file owner.
		Pattern pattern = Pattern.compile("/[1-9]+/");
		Matcher matcher = pattern.matcher(fileLink);
		matcher.find();
		String temp = matcher.group();
		int fileOwnerId = Integer
				.parseInt(temp.substring(1, temp.length() - 1));
		String filename = fileLink.substring(fileLink.lastIndexOf("/") + 1);

		// Check if the file was shared.
		JSONObject json = new JSONObject();
		try {
			boolean isShared = FileDAO.getInstance().isFileSharable(
					fileOwnerId, filename);
			
			if(isShared){
				// Copy a file to requesting user.
				String srcFileLink = request.getServletContext().getRealPath("/UserFiles/" + fileOwnerId + "/" + filename);
				String destFileLink = request.getServletContext().getRealPath("/UserFiles/" + userId + "/" + filename);
				
				File srcFile = new File(srcFileLink);
				File destFile = new File(destFileLink);
				FileUtils.copyFile(srcFile, destFile);
				
				json.put("message", "A copy was created under you repository.");
			} else {
				json.put("message", "The file is not under sharing.");
			}
			
			json.put("isSuccess", isShared);
		} catch (Exception e) {
			try {
			json.put("isSuccess", false);
			json.put("message", e.getMessage());
			}catch(Exception ne){}
		}
		
		response.getWriter().print(json);
	}
}
