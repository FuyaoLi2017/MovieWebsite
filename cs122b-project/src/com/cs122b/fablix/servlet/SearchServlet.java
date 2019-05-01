package com.cs122b.fablix.servlet;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.vo.CustomerVo;
import com.cs122b.fablix.entity.vo.MovieListVo;
import com.cs122b.fablix.entity.vo.MovieVo;
import com.cs122b.fablix.service.SearchMovieService;
import com.cs122b.fablix.service.Impl.SearchMovieServiceImpl;
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
 * the URL pattern /api/search
 * 
 * this servlet is used to search the movies
 */
@WebServlet(name = "SearchServlet", urlPatterns = "/api/search")
public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 4388567641898034769L;

	private SearchMovieService searchMovieService = new SearchMovieServiceImpl();

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
				ResponseModel<MovieListVo> serverResponse;
				String title = request.getParameter("title") == null ? "" : request.getParameter("title");
				String year = request.getParameter("year") == null ? "" : request.getParameter("year");
				String director = request.getParameter("director") == null ? "" : request.getParameter("director");
				String star = request.getParameter("star") == null ? "" : request.getParameter("star");
				String sequenceBase = request.getParameter("sequenceBase") == null ? "title" : request.getParameter("sequenceBase");
                String ascOrDes = request.getParameter("ascOrDes") == null ? "" : request.getParameter("ascOrDes");
                int limit = Integer.valueOf(request.getParameter("limit") == null ? "20" : request.getParameter("limit"));
                int offset = Integer.valueOf(request.getParameter("offset") == null ? "0" : request.getParameter("offset"));
                
                System.out.println("ascOrDes: " + ascOrDes);
				serverResponse = searchMovieService.listMoviesBySearchingResult(title, year, director, star,
						sequenceBase, ascOrDes, limit, offset);
				String json = new Gson().toJson(serverResponse);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
		}
	}
}