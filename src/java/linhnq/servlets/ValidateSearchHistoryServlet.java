/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "ValidateSearchHistoryServlet", urlPatterns = {"/ValidateSearchHistoryServlet"})
public class ValidateSearchHistoryServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ValidateSearchHistoryServlet.class);
    private final String HISTORY_PAGE = "shopping_history.jsp";
    private final String SEARCH_ORDER = "SearchOrderServlet";

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

        String url = HISTORY_PAGE;
        boolean err = false;
        try {
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");

            Date from = null;
            Date to = null;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            if (fromDate == null || fromDate.isEmpty()) {
                from = new Date();
            } else if (!fromDate.matches("^(3[01]|[12][0-9]|0?[1-9])/(1[0-2]|0?[1-9])/[0-9]{4}$")) {
                err = true;
                request.setAttribute("FROM_DATE_ERROR", "You must set a valid date");
            } else {
                try {
                    from = formatter.parse(fromDate);
                } catch (ParseException e) {
                    err = true;
                    request.setAttribute("FROM_DATE_ERROR", "You must set a valid date");
                }
            }

            if (toDate == null || toDate.isEmpty()) {
                to = new Date();
            } else if (!toDate.matches("^(3[01]|[12][0-9]|0?[1-9])/(1[0-2]|0?[1-9])/[0-9]{4}$")) {
                err = true;
                request.setAttribute("FROM_DATE_ERROR", "You must set a valid date");
            } else {
                try {
                    to = formatter.parse(toDate);
                } catch (ParseException e) {
                    err = true;
                    request.setAttribute("TO_DATE_ERROR", "You must set a valid date");
                }
            }

            if (!err) {
                url = SEARCH_ORDER;
                request.setAttribute("FROM_DATE", from);
                request.setAttribute("TO_DATE", to);
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
