package com.cs122b.fablix.servlet;

import com.cs122b.fablix.common.Constants;
import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.vo.CustomerVo;
import com.cs122b.fablix.entity.vo.ShoppingMovieVo;
import com.cs122b.fablix.service.MovieService;
import com.cs122b.fablix.service.Impl.MovieServiceImpl;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is declared as ShoppingCartServlet in web annotation, which is mapped to
 * the URL pattern /api/addOrUpdateShoppingCart
 * 
 * this servlet is used to add new items to shopping cart update the previous quantity of items in shopping cart
 * we add the current  to session
 */
@WebServlet(name = "ShoppingCartServlet", urlPatterns = "/api/shoppingCart")
public class ShoppingCartServlet extends HttpServlet {

	private static final long serialVersionUID = 2004686554279977859L;

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
			CustomerVo customerVo = (CustomerVo) session.getAttribute(Constants.CURRENT_CUSTOMER);
			if (customerVo == null) {
				String json = new Gson()
						.toJson(ResponseModel.createByErrorMessage("session not matched to a customer, need to login"));
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			} else {
	                Map<String, ShoppingMovieVo> shoppingCart = new HashMap<String, ShoppingMovieVo>();
	                List<ShoppingMovieVo> shoppingCartList = new ArrayList<>();
	                
	                if (session.getAttribute(Constants.SHOPPING_CART) != null) {
	                	shoppingCart = (Map<String, ShoppingMovieVo>)session.getAttribute(Constants.SHOPPING_CART);
	                	for (Map.Entry<String, ShoppingMovieVo> entry : shoppingCart.entrySet()) {
		                	shoppingCartList.add(entry.getValue());
		                }
		                Collections.sort(shoppingCartList, (o1, o2) -> {
		                	return o1.getMovieVo().getId().compareTo((String)o2.getMovieVo().getId());
		                });
	                }
	                String json = new Gson().toJson(ResponseModel.createBySuccess("return the current shopping items sorted by movieId", shoppingCartList));
	                response.setContentType("application/json");
	                response.setCharacterEncoding("UTF-8");
	                response.getWriter().write(json);
			}
		}
	}
}