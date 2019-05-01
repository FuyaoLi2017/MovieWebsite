package com.cs122b.fablix.servlet;

import com.cs122b.fablix.common.Constants;
import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.pojo.CreditCard;
import com.cs122b.fablix.entity.pojo.SalesRecord;
import com.cs122b.fablix.entity.vo.CustomerVo;
import com.cs122b.fablix.entity.vo.ShoppingMovieVo;
import com.cs122b.fablix.service.CardService;
import com.cs122b.fablix.service.SalesService;
import com.cs122b.fablix.service.Impl.CardServiceImpl;
import com.cs122b.fablix.service.Impl.SalesServiceImpl;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is declared as CheckOutServlet in web annotation, which is mapped,
 * do card check and process to checkout if the card information is correct to
 * the URL pattern /api/checkOut
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = "/api/checkOut")
public class CheckOutServlet extends HttpServlet {

	private static final long serialVersionUID = -4196379597527225581L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	private CardService cardService = new CardServiceImpl();

	private SalesService salesService = new SalesServiceImpl();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String cardId = request.getParameter("cardId");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String expiration = request.getParameter("expiration");
		
		System.out.println("cardId: " + cardId);
		System.out.println("firstName" + firstName);
		System.out.println("lastName" + lastName);
		System.out.println("expiration" + expiration);

		String message = "";

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
			} else { // check the card information is completed or not
				System.out.println("checking card information complete or not?");
				if (cardId == null || firstName == null || lastName == null || expiration == null || cardId == ""
						|| firstName == "" || lastName == "" || expiration == "") {

					String json = new Gson().toJson(ResponseModel
							.createByErrorMessage("The card information is not complete, can't proceed to checkout."));
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(json);
				} else { // check the card information is valid or not
					System.out.println("complete info. checking card information valid or not?");
					ResponseModel<CreditCard> serverResponse = cardService.selectCardById(cardId);

					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					String recordCardId = serverResponse.getData().getId();
					String recordFirstName = serverResponse.getData().getFirstName();
					String recordLastName = serverResponse.getData().getLastName();
					Date expire = serverResponse.getData().getExpiration();
					String formattedExpiration = expire == null ? "" : formatter.format(expire);

					recordCardId = (recordCardId == null) ? "" : recordCardId;
					recordFirstName = (recordFirstName == null) ? "" : recordFirstName;
					recordLastName = (recordLastName == null) ? "" : recordLastName;
					formattedExpiration = (formattedExpiration == null) ? "" : formattedExpiration;

					@SuppressWarnings("unchecked")
					Map<String, ShoppingMovieVo> shoppingCart = (HashMap<String, ShoppingMovieVo>) session
							.getAttribute(Constants.SHOPPING_CART);
					if (recordCardId.equals(cardId) && recordFirstName.equals(firstName)
							&& recordLastName.equals(lastName) && formattedExpiration.equals(expiration)) {
						System.out.println("valid info, insert to database");
						// the card is valid
						
						List<SalesRecord> salesRecordsList = new ArrayList<>();

						for (String movieId : shoppingCart.keySet()) {
							SalesRecord insertToSalesRecords = salesService.updateSalesRecord(customerVo.getId(),
									movieId, shoppingCart.get(movieId).getMovieVo().getTitle(), shoppingCart.get(movieId).getQuantity());
							
							if (insertToSalesRecords.getSalesIdList().size() != shoppingCart.get(movieId)
									.getQuantity()) { // if the server fails to insert some data into database
								message = "Fail to insert data into database";
								String json = new Gson().toJson(ResponseModel.createByErrorMessage(message));
								response.setContentType("application/json");
								response.setCharacterEncoding("UTF-8");
								response.getWriter().write(json);
								break;
							} else { // the server is able to insert this records to the database
								salesRecordsList.add(insertToSalesRecords);
							}
						}
						
						if (!message.equals("Fail to insert data into database")) {
							shoppingCart.clear();
							session.setAttribute(Constants.SHOPPING_CART, shoppingCart);
							message = "Checkout Successfully";
							String json = new Gson().toJson(ResponseModel.createBySuccess("a list of purchased movies and their sales ids", salesRecordsList));
							response.setContentType("application/json");
							response.setCharacterEncoding("UTF-8");
							response.getWriter().write(json);
						}

					} else { // the card is invalid
						message = "The card information is invalid";
						String json = new Gson().toJson(ResponseModel.createByErrorMessage(message));
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(json);
					}
				}
			}
		}
	}
}
