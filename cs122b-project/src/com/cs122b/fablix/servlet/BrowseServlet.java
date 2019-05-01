package com.cs122b.fablix.servlet;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.common.ResponseStatus;
import com.cs122b.fablix.entity.pojo.Customer;
import com.cs122b.fablix.entity.vo.CustomerVo;
import com.cs122b.fablix.entity.vo.MovieListVo;
import com.cs122b.fablix.entity.vo.MovieVo;
import com.cs122b.fablix.service.BrowseMovieService;
import com.cs122b.fablix.service.Impl.BrowseMovieServiceImpl;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * This class is declared as BrowseServlet in web annotation, 
 * which is mapped to the URL pattern /api/browse
 */
@WebServlet(name = "BrowseServlet", urlPatterns = "/api/browse")
public class BrowseServlet extends HttpServlet {

	private static final long serialVersionUID = -6698806756843059057L;
	
	private BrowseMovieService browseMovieService = new BrowseMovieServiceImpl();

	/**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
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
                String json = new Gson().toJson(ResponseModel.createByErrorMessage("session not matched to a customer, need to login"));
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
    	} else {
    		ResponseModel<MovieListVo> serverResponse;
    		String browseType = request.getParameter("browseType");
    		String category = request.getParameter("category");
    		String sequenceBase = request.getParameter("sequenceBase") == null ? "title" : request.getParameter("sequenceBase");
    		String ascOrDes = request.getParameter("ascOrDes") == null ? "" : request.getParameter("ascOrDes");
            int limit = Integer.parseInt(request.getParameter("limit") == null ? "20" : request.getParameter("limit"));
            int offset = Integer.parseInt(request.getParameter("offset") == null ? "0" : request.getParameter("offset"));
            
            if (browseType.equals("genre")) {   // browse by genre
                serverResponse = browseMovieService.listMoviesByGenre(category, sequenceBase, ascOrDes, limit, offset);
                String json = new Gson().toJson(serverResponse);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } else if (browseType.equals("firstLetter")){  // browse by first letter
                serverResponse = browseMovieService.listMoviesByTitle(category, sequenceBase, ascOrDes, limit, offset);
                String json = new Gson().toJson(serverResponse);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } else {     // invalid input
            	String json = new Gson().toJson(ResponseModel.createByErrorMessage("invalid browse type"));
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            }
    	}
    }
}
}
