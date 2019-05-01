package com.cs122b.fablix.servlet.backstage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cs122b.fablix.common.Constants;
import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.pojo.Employee;
import com.cs122b.fablix.entity.pojo.Star;
import com.cs122b.fablix.service.StarService;
import com.cs122b.fablix.service.Impl.StarServiceImpl;
import com.google.gson.Gson;

/**
 * This class is declared as AddNewStarServlet in web annotation, 
 * which is mapped to the URL pattern /api/addGenre
 */
@WebServlet(name = "AddNewStarServlet", urlPatterns = "/api/addStar")
public class AddNewStarServlet extends HttpServlet {

	private static final long serialVersionUID = -3871671922769831161L;

	private StarService starService = new StarServiceImpl();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// try to get the current session
		HttpSession session = request.getSession(false);

		if (session == null) {
			String json = new Gson().toJson(ResponseModel.createByErrorMessage("Need to login"));
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		} else {
			Employee employee = (Employee) session.getAttribute(Constants.CURRENT_EMPLOYEE);
			if (employee == null) {
				String json = new Gson()
						.toJson(ResponseModel.createByErrorMessage("session not matched to a employee, need to login"));
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			} else {
				ResponseModel<Star> serverResponse;
				String starName = request.getParameter("starName");
				String starBirthYear = request.getParameter("birthYear");
				// the birthYear could be null
				int birthYear =  -1;
				if (starBirthYear != null) {
					birthYear = Integer.valueOf(starBirthYear);
				}
				serverResponse = starService.addNewStar(starName, birthYear);
				String json = new Gson().toJson(serverResponse);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
		}
	}
}
