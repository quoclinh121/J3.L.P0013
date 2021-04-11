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
import linhnq.daos.TblItemsDAO;
import linhnq.dtos.TblItemsDTO;
import linhnq.dtos.TblUsersDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchServlet.class);
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

        String url = GUEST_PAGE;

        try {
            HttpSession session = request.getSession();

            int pageSize = 5;
            int pageNumber = 1;

            if (request.getAttribute("CURRENT_PAGE") != null) {
                pageNumber = (int) request.getAttribute("CURRENT_PAGE");
            }

            if (request.getParameter("page") != null) {
                pageNumber = Integer.parseInt(request.getParameter("page"));
            }
            String category = null;
            String searchValue = null;
            int range = 0;

            if ("true".equals(request.getParameter("isClickSearch"))) {
                category = request.getParameter("cbxCategory");
                searchValue = request.getParameter("txtSearchValue");
                range = Integer.parseInt(request.getParameter("range_price"));
                session.setAttribute("CATEGORY", category);
                session.setAttribute("SEARCH_VALUE", searchValue);
                session.setAttribute("RANGE", range);
            } else {
                category = (String) session.getAttribute("CATEGORY");
                searchValue = (String) session.getAttribute("SEARCH_VALUE");
                range = (int) session.getAttribute("RANGE");
            }

            TblItemsDAO dao = new TblItemsDAO();
            List<TblItemsDTO> items = null;
            int noOfRecord = 0;
            int noOfPage = 0;

            TblUsersDTO dto = (TblUsersDTO) session.getAttribute("LOGIN_USER");
            if (dto != null) {
                if ("ad".equals(dto.getRoleID())) {
                    if (!"All Categories".equals(category)) {
                        items = dao.searchItems(searchValue, range, category, pageNumber, pageSize, "ad");
                        noOfRecord = dao.getNoOfRecordForSearch(searchValue, range, category, "ad");
                    } else {
                        items = dao.searchItems(searchValue, range, "", pageNumber, pageSize, "ad");
                        noOfRecord = dao.getNoOfRecordForSearch(searchValue, range, "", "ad");
                    }
                    url = ADMIN_PAGE;
                } else {
                    if (!"All Categories".equals(category)) {
                        items = dao.searchItems(searchValue, range, category, pageNumber, pageSize, "us");
                        noOfRecord = dao.getNoOfRecordForSearch(searchValue, range, category, "us");
                    } else {
                        items = dao.searchItems(searchValue, range, "", pageNumber, pageSize, "us");
                        noOfRecord = dao.getNoOfRecordForSearch(searchValue, range, "", "us");
                    }
                    url = SEARCH_PAGE;
                }
            } else {
                if (!"All Categories".equals(category)) {
                    items = dao.searchItems(searchValue, range, category, pageNumber, pageSize, "us");
                    noOfRecord = dao.getNoOfRecordForSearch(searchValue, range, category, "us");
                } else {
                    items = dao.searchItems(searchValue, range, "", pageNumber, pageSize, "guest");
                    noOfRecord = dao.getNoOfRecordForSearch(searchValue, range, "", "guest");
                }
            }

            noOfPage = (int) Math.ceil(noOfRecord * 1.0 / pageSize);
            session.setAttribute("ITEM_LIST", items);
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
