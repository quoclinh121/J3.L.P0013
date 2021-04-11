/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.dtos;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author quocl
 */
public class TblOrdersDTO implements Serializable {
    private String orderID;
    private int totalPrice;
    private Date paymentDate;
    private String paymentMethod;
    private String userID;

    public TblOrdersDTO(String orderID, int totalPrice, Date paymentDate, String paymentMethod, String userID) {
        this.orderID = orderID;
        this.totalPrice = totalPrice;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.userID = userID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    
}
