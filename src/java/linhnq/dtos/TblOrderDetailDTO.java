/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.dtos;

import java.io.Serializable;

/**
 *
 * @author quocl
 */
public class TblOrderDetailDTO implements Serializable {
    private String orderID;
    private String item;
    private int quantity;

    public TblOrderDetailDTO(String orderID, String item, int quantity) {
        this.orderID = orderID;
        this.item = item;
        this.quantity = quantity;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
 
}
