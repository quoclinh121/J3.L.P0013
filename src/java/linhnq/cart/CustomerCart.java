/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import linhnq.dtos.TblItemsDTO;

/**
 *
 * @author quocl
 */
public class CustomerCart implements Serializable {

    private Map<String, TblItemsDTO> items;

    public Map<String, TblItemsDTO> getItems() {
        return items;
    }

    public void addItemToCart(TblItemsDTO dto) {
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        String key = dto.getItemID();
        if (this.items.containsKey(key)) {
            int temp = dto.getQuantity() - items.get(key).getQuantity();
            dto.setQuantity(dto.getQuantity() - temp + 1);
        } else {
            dto.setQuantity(1);
        }

        this.items.put(dto.getItemID(), dto);
    }

    public void descreaseQuantity(TblItemsDTO dto) {
        String key = dto.getItemID();
        if (this.items.containsKey(key)) {
            int temp = dto.getQuantity() - items.get(key).getQuantity();
            dto.setQuantity(dto.getQuantity() - temp - 1);
        }
        this.items.put(dto.getItemID(), dto);
    }
    
    public void removeItemFromCart(String key) {
        if(this.items == null) {
            return;
        }
        
        if(this.items.containsKey(key)) {
            this.items.remove(key);
            if(this.items.isEmpty()) {
                this.items = null;
            }
        }
    }
}
