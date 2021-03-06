package com.cs122b.fablix.servlet.backstage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cs122b.fablix.common.Constants;
import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.pojo.Employee;
import com.cs122b.fablix.entity.pojo.Genre;
import com.cs122b.fablix.service.GenresService;
import com.cs122b.fablix.service.Impl.GenresServiceImpl;
import com.google.gson.Gson;

/**
 * This class is declared as SearchGenreServlet in web annotation, 
 * which is mapped to the URL pattern /api/searchGenres
 */
@WebServlet(name = "SearchGenreServlet", urlPatterns = "/api/searchGenre")
public class SearchGenreServlet extends HttpServlet {

	private static final long serialVersionUID = -6149002443807352822L;

	private GenresService genresService = new GenresServiceImpl();
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
				ResponseModel<List<Genre>> serverResponse;
				String partialGenre = request.getParameter("partialGenre");
				partialGenre = partialGenre.trim();
				
				serverResponse = genresService.selectGenreBySubstring(partialGenre);
				String json = new Gson().toJson(serverResponse);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
		}
	}
}