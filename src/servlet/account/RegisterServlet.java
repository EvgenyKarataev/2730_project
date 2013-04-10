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

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String confirm = request.getParameter("confirmPassword");
		
		// Returned data.
		boolean isSuccess = false;
		String message = "";
		
		// Validate data.
		try{
			AccountDAO dao = AccountDAO.getInstance();
			dao.Register(userName, password, confirm);
			
			isSuccess = true;
			message = "Your account was created";
			
		}catch(Exception e){
			message = e.getMessage();
		}
		
		JSONObject json = new JSONObject(new FormMessageModel(isSuccess, message));
		response.getWriter().print(json);
	}

}
