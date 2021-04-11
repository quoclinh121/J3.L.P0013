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
public class CheckItemError implements Serializable {
    private String itemNameLengthErr;
    private String itemPriceFormatErr;
    private String itemQuantityFormatErr;
    private String imageErr;
    private String descriptionLengthErr;

    public CheckItemError() {
    }

    public CheckItemError(String itemNameLengthErr, String itemPriceFormatErr, String itemQuantityFormatErr, String imageErr, String descriptionLengthErr) {
        this.itemNameLengthErr = itemNameLengthErr;
        this.itemPriceFormatErr = itemPriceFormatErr;
        this.itemQuantityFormatErr = itemQuantityFormatErr;
        this.imageErr = imageErr;
        this.descriptionLengthErr = descriptionLengthErr;
    }

    public String getItemNameLengthErr() {
        return itemNameLengthErr;
    }

    public void setItemNameLengthErr(String itemNameLengthErr) {
        this.itemNameLengthErr = itemNameLengthErr;
    }

    public String getItemPriceFormatErr() {
        return itemPriceFormatErr;
    }

    public void setItemPriceFormatErr(String itemPriceFormatErr) {
        this.itemPriceFormatErr = itemPriceFormatErr;
    }

    public String getItemQuantityFormatErr() {
        return itemQuantityFormatErr;
    }

    public void setItemQuantityFormatErr(String itemQuantityFormatErr) {
        this.itemQuantityFormatErr = itemQuantityFormatErr;
    }

    public String getImageErr() {
        return imageErr;
    }

    public void setImageErr(String imageErr) {
        this.imageErr = imageErr;
    }

    public String getDescriptionLengthErr() {
        return descriptionLengthErr;
    }

    public void setDescriptionLengthErr(String descriptionLengthErr) {
        this.descriptionLengthErr = descriptionLengthErr;
    }
    
    
}
