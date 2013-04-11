package servlet.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.FormMessageModel;

import org.json.JSONObject;

import dao.AccountDAO;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("userId") != null) {
			response.sendRedirect("CodeEditor");
		} else {
			request.getRequestDispatcher("/Login.jsp").forward(request,
					response);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");

		// Returned data.
		boolean isSuccess = false;
		String message = "";
		int userId = -1;

		// Validate data.
		try {
			AccountDAO dao = AccountDAO.getInstance();
			userId = dao.Login(userName, password);
			if (userId >= 0)
				isSuccess = true;

			message = isSuccess ? "You have logged in"
					: "Your username-password pair is incorrect.";
		} catch (Exception e) {
			message = e.getMessage();
		}

		// If login, add user info into session.
		if (isSuccess) {
			request.getSession().setAttribute("userId", userId);
			request.getSession().setAttribute("userName", userName);
		}

		JSONObject json = new JSONObject(new FormMessageModel(isSuccess,
				message));
		response.getWriter().print(json);
	}

}
