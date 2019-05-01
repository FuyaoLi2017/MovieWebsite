package com.cs122b.fablix.servlet;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.vo.CustomerVo;
import com.cs122b.fablix.entity.vo.MovieVo;
import com.cs122b.fablix.entity.vo.ShoppingMovieVo;
import com.cs122b.fablix.service.MovieService;
import com.cs122b.fablix.service.SearchMovieService;
import com.cs122b.fablix.service.Impl.MovieServiceImpl;
import com.cs122b.fablix.service.Impl.SearchMovieServiceImpl;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is declared as SearchServlet in web annotation, which is mapped to
 * the URL pattern /api/addOrUpdateShoppingCart
 * 
 * this servlet is used to add new items to shopping cart update the previous quantity of items in shopping cart
 * we add the current  to session
 */
@WebServlet(name = "AddOrUpdateShoppingCartServlet", urlPatterns = "/api/addOrUpdateShoppingCart")
public class AddOrUpdateShoppingCartServlet extends HttpServlet {

	private static final long serialVersionUID = -8402431755817207558L;
	
	private MovieService movieService = new MovieServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
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
	                Map<String, ShoppingMovieVo> shoppingCart = new HashMap<String, ShoppingMovieVo>();
	                if (session.getAttribute("currentShoppingCart") != null) {
	                	shoppingCart = (Map<String, ShoppingMovieVo>)session.getAttribute("currentShoppingCart");
	                }
	                String movieId = request.getParameter("movieId");
	                int count = Integer.parseInt(request.getParameter("count") == null ? "1" : request.getParameter("count"));
	                if (!shoppingCart.containsKey(movieId)) {
	                	ShoppingMovieVo shoppingMovieVo = new ShoppingMovieVo();
	                    ResponseModel<MovieVo> serverResponse = movieService.selectMovieByMovieId(movieId);
	                    shoppingMovieVo.setMovieVo(serverResponse.getData());
	                    shoppingMovieVo.setQuantity(count);
	                    shoppingCart.put(movieId, shoppingMovieVo);
	                } else { // the count will be set to the requested quantity and stored in the session
	                	ShoppingMovieVo shoppingMovieVo = shoppingCart.get(movieId);
	                    shoppingMovieVo.setQuantity(count);
	                    shoppingCart.put(movieId, shoppingMovieVo);
	                }
	                session.setAttribute("currentShoppingCart", shoppingCart);
	                String json = new Gson().toJson(ResponseModel.createBySuccessMessage("add/update a movie in shopping cart, movieId: " + movieId + ", count: " + count));
	                response.setContentType("application/json");
	                response.setCharacterEncoding("UTF-8");
	                response.getWriter().write(json);
			}
		}
	}
}