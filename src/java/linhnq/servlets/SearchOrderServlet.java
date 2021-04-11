/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnq.daos.TblOrderDetailsDAO;
import linhnq.daos.TblOrdersDAO;
import linhnq.dtos.TblOrderDetailDTO;
import linhnq.dtos.TblOrdersDTO;
import linhnq.dtos.TblUsersDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "SearchOrderServlet", urlPatterns = {"/SearchOrderServlet"})
public class SearchOrderServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchOrderServlet.class);
    private final String SEARCH = "search_page.jsp";
    private final String HISTORY_PAGE = "shopping_history.jsp";

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

        String url = SEARCH;
        try {
            String searchValue = request.getParameter("txtSearchValue");
            Date fromDate = (Date) request.getAttribute("FROM_DATE");
            Date tomDate = (Date) request.getAttribute("TO_DATE");

            HttpSession session = request.getSession();
            TblUsersDTO user = (TblUsersDTO) session.getAttribute("LOGIN_USER");

            TblOrdersDAO orderDAO = new TblOrdersDAO();
            List<TblOrdersDTO> listOrder = orderDAO.searchOrder(user.getUserID(), searchValue, fromDate, tomDate);

            TblOrderDetailsDAO orderDetailDAO = new TblOrderDetailsDAO();

            Map<TblOrdersDTO, List<TblOrderDetailDTO>> listOrderDetail = new HashMap<>();

            if (listOrder != null) {
                for (TblOrdersDTO dto : listOrder) {
                    listOrderDetail.put(dto, orderDetailDAO.getItemsInOrder(dto.getOrderID()));
                }
            }

            session.setAttribute("LIST_ORDERS_DETAILS", listOrderDetail);
            url = HISTORY_PAGE;
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
