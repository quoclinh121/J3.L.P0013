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
public class TblUsersDTO implements Serializable {
    private String userID;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private boolean status;
    private String roleID;

    public TblUsersDTO(String userID, String password, String fullname, String email, String phone, boolean status, String roleID) {
        this.userID = userID;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.roleID = roleID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
    
    
}
