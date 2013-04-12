package servlet.file;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dao.FileDAO;

/**
 * Servlet implementation class ShareFile
 */
@WebServlet("/MediaContainer/ShareFile")
public class ShareFile extends HttpServlet {
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("userId") == null)
			return;

		int userId = Integer.parseInt(request.getSession()
				.getAttribute("userId").toString());
		String filename = request.getParameter("filename");

		boolean isSuccess = true;
		String fileLink = "/2730_project/UserFiles/" + userId + "/" + filename;
		try {
			FileDAO.getInstance().shareFile(userId, filename);
		} catch (Exception e) {
			isSuccess = false;
			fileLink = e.getMessage();
		}

		JSONObject json = new JSONObject();
		try {
			json.put("isSuccess", isSuccess);
			json.put("fileLink", fileLink);
		} catch (Exception e) {
		}
		response.getWriter().print(json);
	}
}
