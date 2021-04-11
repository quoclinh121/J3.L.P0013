/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnq.accessgg.GooglePojo;
import linhnq.accessgg.GoogleUtils;
import linhnq.daos.TblUsersDAO;
import linhnq.dtos.TblUsersDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "LoginGoogleSevlet", urlPatterns = {"/LoginGoogleSevlet"})
public class LoginGoogleSevlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginGoogleSevlet.class);
    private static final long serialVersionUID = 1L;
    private static final String ERROR = "login.jsp";
    private static final String SUCCESS = "search_page.jsp";

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

        String url = ERROR;
        try {

            String code = request.getParameter("code");
            if (code != null || !code.isEmpty()) {
                TblUsersDAO dao = new TblUsersDAO();
                HttpSession session = request.getSession();

                String accessToken = GoogleUtils.getToken(code);
                GooglePojo gPojo = GoogleUtils.getUserInfo(accessToken);
                String userID = gPojo.getId();
                TblUsersDTO user = dao.checkLoginGG(userID);
                if (user == null) {
                    String gmail = gPojo.getEmail();
                    user = new TblUsersDTO(userID, "", gmail, gmail, "", true, "us");
                    dao.createUserGG(user);
                }
                session.setAttribute("LOGIN_USER", user);
                url = SUCCESS;
            }

        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
