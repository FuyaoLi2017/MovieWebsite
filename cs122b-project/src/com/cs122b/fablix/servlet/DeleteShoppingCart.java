package com.cs122b.fablix.servlet;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.vo.CustomerVo;
import com.cs122b.fablix.entity.vo.ShoppingMovieVo;
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
 * This class is declared as DeleteShoppingCartServlet in web annotation, which
 * is mapped to the URL pattern /api/deleteShoppingCartServlet
 * 
 * this servlet is used to add new items to shopping cart update the previous
 * quantity of items in shopping cart we add the current to session
 */
@WebServlet(name = "DeleteShoppingCartServlet", urlPatterns = "/api/deleteShoppingCart")
public class DeleteShoppingCart extends HttpServlet {

	private static final long serialVersionUID = -5610605008439850714L;

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
					shoppingCart = (Map<String, ShoppingMovieVo>) session.getAttribute("currentShoppingCart");
				}
				// get the movie id the user want to delete
				String movieId = request.getParameter("movieId");
				if (!shoppingCart.containsKey(movieId)) {
					String json = new Gson().toJson(
							ResponseModel.createByErrorMessage("Deleting a non-existing movie! movieId: " + movieId));
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(json);
				} else { // the count will be set to the requested quantity and stored in the session
					shoppingCart.remove(movieId);
					session.setAttribute("currentShoppingCart", shoppingCart);
					String json = new Gson().toJson(ResponseModel.createBySuccessMessage(
							"successfully deleted a movie in shopping cart, movieId: " + movieId));
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(json);
				}
			}
		}
	}
}