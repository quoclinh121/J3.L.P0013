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
public class TblItemsDTO implements Serializable {
    private String itemID;
    private String itemName;
    private int quantity;
    private int price;
    private Date createDate;
    private String description;
    private String image;
    private String categoryID;
    private boolean status;

    public TblItemsDTO(String itemID, String itemName, int quantity, int price, Date createDate, String description, String image, String categoryID, boolean status) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.createDate = createDate;
        this.description = description;
        this.image = image;
        this.categoryID = categoryID;
        this.status = status;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
}
