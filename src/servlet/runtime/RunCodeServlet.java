package servlet.runtime;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import runtime.RTIPRuntime;
import runtime.RTIPRuntime.RuntimeResult;
import runtime.RuntimeFactory;
import runtime.RuntimeFactory.RuntimeType;

@WebServlet("/RunCode")
public class RunCodeServlet extends HttpServlet {
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	// Run code and return output from compiler.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Type of programming language.
		String runtimeType = request.getParameter("runtimeType");
		
		// Code.
		String code = request.getParameter("code");
		
		// Get runtime based on runtimeType
		RTIPRuntime runtime = null;
		if(runtimeType.equals("Java")){
			runtime = RuntimeFactory.createRuntime(RuntimeType.Java);
		}else if(runtimeType.equals("Python")){
			runtime = RuntimeFactory.createRuntime(RuntimeType.Python);
		}
		
		// Run code.
		RuntimeResult runtimeResult = runtime.runCode(code);
		
		// Output result.
		response.getWriter().print(new JSONObject(runtimeResult));
	}
}
