package servlet.file;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MediaContainer/DeleteFile")
public class Delete extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String filename = request.getParameter("delfile");
		File file = new File(request.getServletContext().getRealPath(
				"/UserFiles/" + request.getSession().getAttribute("userId")
						+ "/" + filename));
		file.delete();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String filename = request.getParameter("filename");
		File file = new File(request.getServletContext().getRealPath(
				"/UserFiles/" + request.getSession().getAttribute("userId")
						+ "/" + filename));
		file.delete();
	}

	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) {
		String filename = request.getParameter("delfile");
		File file = new File(request.getServletContext().getRealPath(
				"/UserFiles/" + request.getSession().getAttribute("userId")
						+ "/" + filename));
		file.delete();
	}
}
