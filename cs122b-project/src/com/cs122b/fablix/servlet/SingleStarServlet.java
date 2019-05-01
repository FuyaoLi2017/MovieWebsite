package com.cs122b.fablix.servlet;

import com.cs122b.fablix.common.Constants;
import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.pojo.Movie;
import com.cs122b.fablix.entity.pojo.Star;
import com.cs122b.fablix.entity.vo.CustomerVo;
import com.cs122b.fablix.entity.vo.MovieVo;
import com.cs122b.fablix.entity.vo.StarVo;
import com.cs122b.fablix.service.MovieService;
import com.cs122b.fablix.service.StarService;
import com.cs122b.fablix.service.Impl.MovieServiceImpl;
import com.cs122b.fablix.service.Impl.StarServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

// Declaring a WebServlet called SingleStarServlet, which maps to url "/single-star"
@WebServlet(name = "SingleStarServlet", urlPatterns = "/api/single-star")
public class SingleStarServlet extends HttpServlet {

	private static final long serialVersionUID = -8162028900831386723L;

	private StarService starService = new StarServiceImpl();
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
					CustomerVo customerVo = (CustomerVo) session.getAttribute(Constants.CURRENT_CUSTOMER);
					if (customerVo == null) {
						String json = new Gson()
								.toJson(ResponseModel.createByErrorMessage("session not matched to a customer, need to login"));
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(json);
					} else {
						String starId = request.getParameter("id");
						StarVo starVo = new StarVo();
						Star star = starService.selectStarByStarId(starId);
						System.out.println("starid: " + star.getId());
						System.out.println("starbirth: " + star.getBirthYear());
						List<Movie> moviesList = movieService.selectMovieByStarId(starId);
						starVo.setId(star.getId());
						starVo.setName(star.getName());
						starVo.setBirthYear(star.getBirthYear());
						starVo.setMoviesList(moviesList);
						
						String json = new Gson().toJson(ResponseModel.createBySuccess("a starVo select by starId: " + starId, starVo));
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(json);
					}
				}

	}

}