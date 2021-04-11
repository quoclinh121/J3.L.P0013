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
import linhnq.dtos.CheckItemError;
import linhnq.dtos.TblItemsDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "CheckValidateAddNewServlet", urlPatterns = {"/CheckValidateAddNewServlet"})
public class CheckValidateAddNewServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CheckValidateAddNewServlet.class);
    private final String ADD_NEW_ITEM = "AddNewItemServlet";
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
            boolean err = false;
            String itemName = request.getParameter("txtItemName");
            String itemPrice = request.getParameter("txtItemPrice");
            int price = 0;
            String itemQuantity = request.getParameter("txtItemQuantity");
            int quantity = 0;
            String category = request.getParameter("cbxCategory");
            String imageLink = request.getParameter("imgItem");
            String description = request.getParameter("txtDescription");

            CheckItemError errors = new CheckItemError();

            if (itemName.length() == 0 || itemName.length() > 50) {
                errors.setItemNameLengthErr("Item name is not allow empty or length greater than 50");
                err = true;
            }

            if (itemPrice.length() == 0) {
                errors.setItemPriceFormatErr("Price is not allow empty");
                err = true;
            } else if (!itemPrice.matches("\\d+")) {
                errors.setItemPriceFormatErr("Price must be an integer");
                err = true;
            } else {
                price = Integer.parseInt(itemPrice);
                if (price <= 0) {
                    errors.setItemPriceFormatErr("Price must be greater than 0");
                    err = true;
                }
            }

            if (itemQuantity.length() == 0) {
                errors.setItemQuantityFormatErr("Quanity is not allow empty");
                err = true;
            } else if (!itemQuantity.matches("\\d+")) {
                errors.setItemQuantityFormatErr("Quantity must be an integer");
                err = true;
            } else {
                quantity = Integer.parseInt(itemQuantity);
                if (quantity <= 0) {
                    errors.setItemQuantityFormatErr("Quantity must be greater than 0");
                    err = true;
                }
            }

            if (description.length() == 0 || description.length() > 500) {
                errors.setDescriptionLengthErr("Description is not allow empty or length greater than 500 characters");
                err = true;
            }

            String img = null;
            if (imageLink.length() == 0) {
                errors.setImageErr("Image is required");
                err = true;
            } else if (!imageLink.contains(".png") && !imageLink.contains(".jpeg") && !imageLink.contains(".jpg") && imageLink.length() != 0) {
                errors.setImageErr("Image only allow file name extensions are .jpeg, .jpg, .png ");
                err = true;
            } else if (imageLink.length() != 0) {
                String[] f = imageLink.split("\\\\");
                img = f[f.length - 1];
            }

            TblItemsDTO dto = new TblItemsDTO(null, itemName, quantity, price, new Date(), description, img, category, true);
            request.setAttribute("NEW_ITEM", dto);

            if (err) {
                request.setAttribute("ADD_ERROR", errors);
                url = ADD_NEW_PAGE;
            } else {
                request.setAttribute("IMAGE_PATH", imageLink);
                url = ADD_NEW_ITEM;
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
