/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnq.daos.TblCategoriesDAO;
import linhnq.daos.TblItemsDAO;
import linhnq.dtos.TblItemsDTO;
import linhnq.dtos.TblUsersDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "FirstController", urlPatterns = {"/FirstController"})
public class FirstController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(FirstController.class);
    private final String SEARCH_PAGE = "search_page.jsp";
    private final String ADMIN_PAGE = "admin_page.jsp";
    private final String GUEST_PAGE = "guest_page.jsp";

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

        String url = ADMIN_PAGE;
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("ALL_CATEGORIES") == null) {
                TblCategoriesDAO categoryDAO = new TblCategoriesDAO();
                List<String> categories = categoryDAO.getAllCategories();
                session.setAttribute("ALL_CATEGORIES", categories);
            }

            int pageSize = 5;
            int pageNumber = 1;
            if (request.getParameter("page") != null) {
                pageNumber = Integer.parseInt(request.getParameter("page"));
            }

            TblItemsDAO itemDAO = new TblItemsDAO();
            List<TblItemsDTO> items = null;
            int noOfRecords = 0;

            TblUsersDTO user = (TblUsersDTO) session.getAttribute("LOGIN_USER");
            if (user != null) {
                if ("ad".equals(user.getRoleID())) {
                    url = ADMIN_PAGE;
                    items = itemDAO.getItems(pageSize, pageNumber, "ad");
                    noOfRecords = itemDAO.getNoOfRecord("ad");
                } else {
                    url = SEARCH_PAGE;
                    items = itemDAO.getItems(pageSize, pageNumber, "us");
                    noOfRecords = itemDAO.getNoOfRecord("us");
                }
            } else {
                url = GUEST_PAGE;
                items = itemDAO.getItems(pageSize, pageNumber, "guest");
                noOfRecords = itemDAO.getNoOfRecord("guest");
            }

            session.setAttribute("ITEM_LIST", items);

            int noOfPage = (int) Math.ceil(noOfRecords * 1.0 / pageSize);
            request.setAttribute("NO_OF_PAGE", noOfPage);
            request.setAttribute("CURRENT_PAGE", pageNumber);
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
