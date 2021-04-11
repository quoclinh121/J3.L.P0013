/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.servlets;

import java.io.IOException;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnq.cart.CustomerCart;
import linhnq.daos.TblItemsDAO;
import linhnq.daos.TblOrderDetailsDAO;
import linhnq.daos.TblOrdersDAO;
import linhnq.dtos.TblItemsDTO;
import linhnq.dtos.TblUsersDTO;
import linhnq.utils.RandomID;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CheckOutServlet.class);
    private final String SHOPPING = "CheckPageServlet";

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

        String url = SHOPPING;
        try {
            HttpSession session = request.getSession();
            CustomerCart cart = (CustomerCart) session.getAttribute("CUSTCART");

            String orderID = RandomID.randomString(10);
            int total = Integer.parseInt(request.getParameter("total"));
            TblUsersDTO user = (TblUsersDTO) session.getAttribute("LOGIN_USER");
            String userID = user.getUserID();
            Date d = new Date();

            TblOrdersDAO orderDAO = new TblOrdersDAO();
            TblOrderDetailsDAO orderDetail = new TblOrderDetailsDAO();
            TblItemsDAO itemDAO = new TblItemsDAO();

            if ("paypal".equals(request.getParameter("paymentMethod"))) {
                if (orderDAO.addNewOrders(orderID, userID, total, "Pay online via PayPal", d)) {
                    for (TblItemsDTO item : cart.getItems().values()) {
                        if (orderDetail.addDetailOrder(orderID, item.getItemID(), item.getQuantity())) {
                            itemDAO.updateQuantityItemInStock(item.getItemID(), item.getQuantity());
                        }
                    }
                }
            } else {
                if (orderDAO.addNewOrders(orderID, userID, total, "Cash payment upon delivery", d)) {
                    for (TblItemsDTO item : cart.getItems().values()) {
                        if (orderDetail.addDetailOrder(orderID, item.getItemID(), item.getQuantity())) {
                            itemDAO.updateQuantityItemInStock(item.getItemID(), item.getQuantity());
                        }
                    }
                }
            }
            
            session.removeAttribute("CUSTCART");
            request.setAttribute("CHECKOUT_SUCCESS", "Thanks for your purchase");
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
