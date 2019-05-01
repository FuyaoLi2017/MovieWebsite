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
import com.cs122b.fablix.service.MovieService;
import com.cs122b.fablix.service.StarService;
import com.cs122b.fablix.service.Impl.MovieServiceImpl;
import com.cs122b.fablix.service.Impl.StarServiceImpl;
import com.google.gson.Gson;

/**
 * This class is declared as AddNewMovieServlet in web annotation, 
 * which is mapped to the URL pattern /api/addGenre
 */
@WebServlet(name = "AddNewMovieServlet", urlPatterns = "/api/addMovie")
public class AddNewMovieServlet extends HttpServlet {

	private static final long serialVersionUID = 5314748767115428030L;

	private MovieService movieService = new MovieServiceImpl();
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
				ResponseModel<String> serverResponse;
				
				String title = request.getParameter("title");
                int year = Integer.valueOf(request.getParameter("year"));
                String director = request.getParameter("director");
                // genreId could be null, null means we are adding a new genre
                Integer genreId = null;
                if (request.getParameter("genreId") != null && request.getParameter("genreId").length() != 0) {
                	genreId = Integer.valueOf(request.getParameter("genreId")); 
                }
                String genreName = request.getParameter("genreName");
                // starId could be null, null means we are adding a new star
                String starId = null;
                if (request.getParameter("starId") != null && request.getParameter("starId").length() != 0) {
                	starId = request.getParameter("starId");
                }
                String starName = request.getParameter("starName");
                int birthYear = -1;
                if (request.getParameter("birthYear") != null && !request.getParameter("birthYear").equals("")) {
                	birthYear = Integer.valueOf(request.getParameter("birthYear"));
                }
                // first parameter, 1 means add new movie, 0 means update movie
				serverResponse = movieService.addNewMovie(1, title, year, director, genreId, genreName, starId, starName, birthYear);
				String json = new Gson().toJson(serverResponse);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
		}
	}
}
