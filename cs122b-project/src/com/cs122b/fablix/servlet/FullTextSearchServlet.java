package com.cs122b.fablix.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cs122b.fablix.common.Constants;
import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.vo.AutoCompleteListVo;
import com.cs122b.fablix.entity.vo.AutoCompleteVo;
import com.cs122b.fablix.entity.vo.CustomerVo;
import com.cs122b.fablix.entity.vo.MovieListVo;
import com.cs122b.fablix.entity.vo.MovieVo;
import com.cs122b.fablix.service.SearchMovieService;
import com.cs122b.fablix.service.Impl.SearchMovieServiceImpl;
import com.google.gson.Gson;

/**
 * This class is declared as FullTextSearchServlet in web annotation, which is
 * mapped to the URL pattern /api/fullTextSearch
 * 
 * this servlet is used to search the movies
 */
@WebServlet(name = "FullTextSearchServlet", urlPatterns = "/api/fullTextSearch")
public class FullTextSearchServlet extends HttpServlet {

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
			CustomerVo customerVo = (CustomerVo) session.getAttribute(Constants.CURRENT_CUSTOMER);
			if (customerVo == null) {
				String json = new Gson()
						.toJson(ResponseModel.createByErrorMessage("session not matched to a customer, need to login"));
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			} else {
				try {
					String query = request.getParameter("query") == null ? "" : request.getParameter("query");
					int limit = Integer
							.valueOf(request.getParameter("limit") == null ? "20" : request.getParameter("limit"));
					int offset = Integer
							.valueOf(request.getParameter("offset") == null ? "0" : request.getParameter("offset"));
					
					String sequenceBase = (request.getParameter("sequenceBase") == null || request.getParameter("sequenceBase").length() == 0) ? "title" : request.getParameter("sequenceBase");
					
		    		String ascOrDes = (request.getParameter("ascOrDes") == null || request.getParameter("ascOrDes").length() == 0) ? "" : request.getParameter("ascOrDes");

					ResponseModel<AutoCompleteListVo> serverResponse = searchMovieService
							.listMoviesByFullTextSearch(query, sequenceBase, ascOrDes , limit, offset);

					String json = new Gson().toJson(serverResponse);
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(json);
				} catch (Exception e) {
					System.out.println(e);
					response.sendError(500, e.getMessage());
				}
			}
		}
	}
}