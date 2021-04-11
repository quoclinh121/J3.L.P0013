/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.servlets;

import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnq.cart.CustomerCart;
import linhnq.daos.TblItemsDAO;
import linhnq.dtos.TblItemsDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "CheckAvailableItemServlet", urlPatterns = {"/CheckAvailableItemServlet"})
public class CheckAvailableItemServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CheckAvailableItemServlet.class);
    private final String VIEW_CART_PAGE = "viewCartPage.jsp";
    private final String CHECK_OUT = "CheckOutServlet";
    private final String CHECK_OUT_PAYPAL = "paypal.jsp";

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

        String url = VIEW_CART_PAGE;
        String error = "";
        try {
            HttpSession session = request.getSession();
            CustomerCart cart = (CustomerCart) session.getAttribute("CUSTCART");

            TblItemsDAO dao = new TblItemsDAO();
            for (Map.Entry<String, TblItemsDTO> e : cart.getItems().entrySet()) {
                int quantityInStock = dao.getQuantityOfItem(e.getKey());
                if (e.getValue().getQuantity() > quantityInStock) {
                    error += "The quantity of " + e.getValue().getItemName() + " in cart is exceeded the quantity in the stock(" + quantityInStock + ")<br/>";
                }
            }

            if (!error.isEmpty()) {
                request.setAttribute("OUT_OF_STOCK", error);
                url = VIEW_CART_PAGE;
            } else if(!"paypal".equals(request.getParameter("paymentMethod"))){
                url = CHECK_OUT;
            } else {
                url = CHECK_OUT_PAYPAL;
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
