/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MainController.class);
    private final String FIRST_CONTROLLER = "FirstController";
    private final String LOGIN = "LoginServlet";
    private final String INVALID_PAGE = "invalid.html";
    private final String LOGOUT = "LogoutServlet";
    private final String LOGIN_PAGE = "login.jsp";
    private final String SEARCH = "SearchServlet";
    private final String SEARCH_PAGE = "search_page.jsp";
    private final String CHECK_PAGE = "CheckPageServlet";
    private final String DELETE = "DeleteServlet";
    private final String GET_UPDATE_ITEM = "GetUpdateItemServlet";
    private final String CHECK_VALIDATE_UPDATE = "CheckValidateUpdateServlet";
    private final String ADD_NEW_ITEM_PAGE = "add_new.jsp";
    private final String CHECK_VALIDATE_ADD_NEW = "CheckValidateAddNewServlet";
    private final String ADD_ITEM_TO_CART = "AddItemToCartServlet";
    private final String VIEW_CART_PAGE = "viewCartPage.jsp";
    private final String INCREASE_QUANTITY = "IncreaseQuantityServet";
    private final String DESCREASE_QUANTITY = "DescreaseQuantityServet";
    private final String REMOVE_ITEM_FROM_CART = "RemoveItemFromCartServlet";
    private final String CHECK_AVAILABLE_ITEM = "CheckAvailableItemServlet";
    private final String LOAD_SHOPPING_HISTORY = "LoadShoppingHistoryServlet";
    private final String VALIDATE_SEARCH_ORDER = "ValidateSearchHistoryServlet";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = INVALID_PAGE;
        try {
            String action = request.getParameter("btnAction");
            if (action == null) {
                url = FIRST_CONTROLLER;
            } else if ("Login".equals(action)) {
                url = LOGIN;
            } else if ("Logout".equals(action)) {
                url = LOGOUT;
            } else if ("Login Your Account".equals(action)) {
                url = LOGIN_PAGE;
            } else if ("Search".equals(action)) {
                url = SEARCH;
            } else if ("SearchPage".equals(action)) {
                url = SEARCH_PAGE;
            } else if ("CheckPage".equals(action)) {
                url = CHECK_PAGE;
            } else if ("Delete".equals(action)) {
                url = DELETE;
            } else if ("Update".equals(action)) {
                url = GET_UPDATE_ITEM;
            } else if ("Update Item".equals(action)) {
                url = CHECK_VALIDATE_UPDATE;
            } else if ("Add new Item".equals(action)) {
                url = ADD_NEW_ITEM_PAGE;
            } else if ("Add new".equals(action)) {
                url = CHECK_VALIDATE_ADD_NEW;
            } else if ("Add to cart".equals(action)) {
                url = ADD_ITEM_TO_CART;
            } else if ("View cart".equals(action)) {
                url = VIEW_CART_PAGE;
            } else if ("Increase".equals(action)) {
                url = INCREASE_QUANTITY;
            } else if ("Descrease".equals(action)) {
                url = DESCREASE_QUANTITY;
            } else if ("Remove Item".equals(action)) {
                url = REMOVE_ITEM_FROM_CART;
            } else if ("Checkout".equals(action)) {
                url = CHECK_AVAILABLE_ITEM;
            } else if ("Shopping History".equals(action)) {
                url = LOAD_SHOPPING_HISTORY;
            } else if("Search Order".equals(action)) {
                url = VALIDATE_SEARCH_ORDER;
            } else if("Purchase".equals(action)) {
                url = CHECK_AVAILABLE_ITEM;
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
