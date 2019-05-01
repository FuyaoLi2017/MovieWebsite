package com.cs122b.fablix.servlet;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.pojo.Genre;
import com.cs122b.fablix.entity.vo.CustomerVo;
import com.cs122b.fablix.entity.vo.MovieVo;
import com.cs122b.fablix.service.SearchMovieService;
import com.cs122b.fablix.service.GenresService;
import com.cs122b.fablix.service.Impl.SearchMovieServiceImpl;
import com.cs122b.fablix.service.Impl.GenresServiceImpl;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * This class is declared as SearchServlet in web annotation, which is mapped to
 * the URL pattern /api/genres
 * 
 * this servlet is used to return all available genres
 */
@WebServlet(name = "SelectAllGenresServlet", urlPatterns = "/api/genres")
public class SelectAllGenresServlet extends HttpServlet {

	private static final long serialVersionUID = -6981233633434613207L;
	
	private GenresService genresService = new GenresServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// try to get the current session
		HttpSession session = request.getSession(false);
		if (session == null) {
			String json = new Gson().toJson(ResponseModel.createByErrorMessage("Need to login"));
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		} else {
			CustomerVo customerVo = (CustomerVo) session.getAttribute("currentCustomer");
			if (customerVo == null) {
				String json = new Gson()
						.toJson(ResponseModel.createByErrorMessage("session not matched to a customer, need to login"));
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			} else {
				ResponseModel<List<Genre>> serverResponse;
                
				serverResponse = genresService.selectAllGenres();
				String json = new Gson().toJson(serverResponse);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
		}
		
	}
}