/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnq.dtos.CheckItemError;
import linhnq.dtos.TblItemsDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author quocl
 */
@WebServlet(name = "CheckValidateUpdateServlet", urlPatterns = {"/CheckValidateUpdateServlet"})
public class CheckValidateUpdateServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CheckValidateUpdateServlet.class);
    private final String UPDATE_PAGE = "update_page.jsp";
    private final String UPDATE_ITEM = "UpdateItemServlet";

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

        String url = "";
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
            String status = request.getParameter("cbxStatus");
            boolean s = false;

            CheckItemError errors = new CheckItemError();
            HttpSession session = request.getSession();
            TblItemsDTO dto = (TblItemsDTO) session.getAttribute("UPDATED_ITEM");

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

            if ("True".equals(status)) {
                s = true;
            } else {
                s = false;
            }

            String img = null;
            if (!imageLink.contains(".png") && !imageLink.contains(".jpeg") && !imageLink.contains(".jpg") && imageLink.length() != 0) {
                errors.setImageErr("Image only allow file name extensions are .jpeg, .jpg, .png ");
                err = true;
            } else if (imageLink.length() != 0) {
                String[] f = imageLink.split("\\\\");
                String[] p = f[f.length - 1].split("\\.");
                img = "./pictures/" + dto.getItemID() + "." + p[1];
            }

            if (err) {
                request.setAttribute("UPDATE_ERROR", errors);
                url = UPDATE_PAGE;
            } else {
                dto.setItemName(itemName);
                dto.setPrice(price);
                dto.setQuantity(quantity);
                dto.setCategoryID(category);
                dto.setDescription(description);
                if (img != null) {
                    dto.setImage(img);
                    request.setAttribute("CHANGE_IMG", "True");
                }
                dto.setStatus(s);
                session.setAttribute("UPDATED_ITEM", dto);
                request.setAttribute("IMAGE_PATH", imageLink);
                url = UPDATE_ITEM;
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
