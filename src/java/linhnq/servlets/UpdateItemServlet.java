/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnq.daos.TblItemsDAO;
import linhnq.daos.TblUpdateHistoryDAO;
import linhnq.dtos.TblItemsDTO;
import linhnq.dtos.TblUsersDTO;
import linhnq.utils.RandomID;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "UpdateItemServlet", urlPatterns = {"/UpdateItemServlet"})
public class UpdateItemServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UpdateItemServlet.class);
    private final String SEARCH_PAGE = "CheckPageServlet";

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

        String url = SEARCH_PAGE;
        try {
            HttpSession session = request.getSession();
            TblItemsDTO dto = (TblItemsDTO) session.getAttribute("UPDATED_ITEM");
            TblItemsDAO dao = new TblItemsDAO();

            if (dao.updateItem(dto)) {
                String isChangeImg = (String) request.getAttribute("CHANGE_IMG");
                if ("True".equals(isChangeImg)) {
                    String coppyFileTo = "D:/CN5/LAB231/P0013/web" + dto.getImage().substring(1);
                    Files.copy(Paths.get((String) request.getAttribute("IMAGE_PATH")), Paths.get(coppyFileTo), StandardCopyOption.REPLACE_EXISTING);
                }

                TblUpdateHistoryDAO updateDAO = new TblUpdateHistoryDAO();
                String updateID = RandomID.randomString(20);

                TblUsersDTO userDTO = (TblUsersDTO) session.getAttribute("LOGIN_USER");
                String userID = userDTO.getUserID();

                updateDAO.addNewUpdate(updateID, dto.getItemID(), userID, "update", new Date());
                request.setAttribute("UPDATE_SUCCESS", "Update item name: " + dto.getItemName() + " successfull!");
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
