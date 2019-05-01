package com.cs122b.fablix.servlet.backstage;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cs122b.fablix.common.Constants;
import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.pojo.Employee;
import com.cs122b.fablix.service.EmployeeService;
import com.cs122b.fablix.service.Impl.EmployeeServiceImpl;
import com.google.gson.Gson;

/**
 * This class is declared as AdminLoginServlet in web annotation, 
 * which is mapped to the URL pattern /api/adminLogin
 */
@WebServlet(name = "AdminLoginServlet", urlPatterns = "/api/adminLogin")
public class AdminLoginServlet extends HttpServlet {

	private static final long serialVersionUID = -5196549706528226806L;

	/**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	
	EmployeeService employeeService = new EmployeeServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
//     we need to verify the email and the password to allow the user to login
        ResponseModel<Employee> serverResponse = employeeService.adminLogin(email, password);
        if (serverResponse.getStatus().equals("SUCCESS")) {
        	HttpSession session = request.getSession();
        	session.setAttribute(Constants.CURRENT_EMPLOYEE, serverResponse.getData());
        }
        // if the request is successful, the serverResponse would have status, message and data field
        // if the request fails, the serverResponse would only have status and message field
        String json = new Gson().toJson(serverResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

}
