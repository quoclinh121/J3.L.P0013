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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import linhnq.daos.TblItemsDAO;
import linhnq.dtos.TblItemsDTO;
import linhnq.utils.RandomID;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "AddNewItemServlet", urlPatterns = {"/AddNewItemServlet"})
public class AddNewItemServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddNewItemServlet.class);
    private final String ADD_NEW_PAGE = "add_new.jsp";

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

        String url = ADD_NEW_PAGE;
        try {
            String imagePath = (String) request.getAttribute("IMAGE_PATH");
            TblItemsDTO dto = (TblItemsDTO) request.getAttribute("NEW_ITEM");
            String img = dto.getImage();
            String[] split = img.split("\\.");

            String itemID = RandomID.randomString(5);
            dto.setItemID(itemID);
            dto.setImage("./pictures/" + itemID + "." + split[1]);

            TblItemsDAO dao = new TblItemsDAO();
            if (dao.addNewItem(dto)) {
                String coppyFileTo = "D:/CN5/LAB231/P0013/web" + dto.getImage().substring(1);
                Files.copy(Paths.get(imagePath), Paths.get(coppyFileTo), StandardCopyOption.REPLACE_EXISTING);
                url = "MainController?btnAction=CheckPage&page=1";
                request.setAttribute("ADD_SUCCESS", "Add new item name: " + dto.getItemName() + " successfull!");
            } else {
                url = ADD_NEW_PAGE;
                request.setAttribute("ADD_FAIL", "Add new item fail!!!");
            }
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
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
