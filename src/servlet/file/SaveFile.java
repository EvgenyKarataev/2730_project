package servlet.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

@WebServlet("/MediaContainer/SaveFile")
public class SaveFile extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// User must login to use this function.
		if (request.getSession().getAttribute("userId") == null)
			return;

		int userId = Integer.parseInt(request.getSession()
				.getAttribute("userId").toString());
		String code = request.getParameter("code");
		String filename = request.getParameter("filename");
		boolean isNewFile = Boolean.parseBoolean(request
				.getParameter("isNewFile"));

		File file = new File(request.getServletContext().getRealPath("/")
				+ "UserFiles/" + userId + "/", filename);
		String filenameWithoutExtension = FilenameUtils.getBaseName(filename);
		String extension = FilenameUtils.getExtension(filename);

		// If the file is new, avoid overriding existing files. 
		if (isNewFile) {
			for (int i = 1; file.exists(); i++) {
				file = new File(request.getServletContext().getRealPath("/")
						+ "UserFiles/" + userId + "/", filenameWithoutExtension
						+ "_" + i + "." + extension);
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter(file));
		out.print(code);
		out.close();
	}
}
