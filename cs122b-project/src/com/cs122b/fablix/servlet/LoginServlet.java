package com.cs122b.fablix.servlet;

import com.cs122b.fablix.common.Constants;
import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.vo.CustomerVo;
import com.cs122b.fablix.service.CustomerService;
import com.cs122b.fablix.service.Impl.CustomerServiceImpl;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

/**
 * This class is declared as LoginServlet in web annotation, 
 * which is mapped to the URL pattern /api/login
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/api/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -8201324614178247801L;

	/**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	private CustomerService customerService = new CustomerServiceImpl();
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String mobile = request.getParameter("mobile");
    	if ("mobile".equals(mobile)) {
    		String email = request.getParameter("email");
            String password = request.getParameter("password");
            
//         we need to verify the email and the password to allow the user to login
            ResponseModel<CustomerVo> serverResponse = customerService.mobileLogin(email, password);
            if (serverResponse.getStatus().equals("SUCCESS")) {
            	HttpSession session = request.getSession();
            	session.setAttribute(Constants.CURRENT_CUSTOMER, serverResponse.getData());
            }
            // if the request is successful, the serverResponse would have status, message and data field
            // if the request fails, the serverResponse would only have status and message field
            String json = new Gson().toJson(serverResponse);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
    		
    	} else {
    		String email = request.getParameter("email");
            String password = request.getParameter("password");
            String reCaptcha = request.getParameter("g-recaptcha-response");
            
            System.out.println("gRecaptchaResponse=" + reCaptcha);

//         we need to verify the email and the password to allow the user to login
            ResponseModel<CustomerVo> serverResponse = customerService.customerLogin(email, password, reCaptcha);
            if (serverResponse.getStatus().equals("SUCCESS")) {
            	HttpSession session = request.getSession();
            	session.setAttribute(Constants.CURRENT_CUSTOMER, serverResponse.getData());
            }
            // if the request is successful, the serverResponse would have status, message and data field
            // if the request fails, the serverResponse would only have status and message field
            String json = new Gson().toJson(serverResponse);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
    	}
    }
}
