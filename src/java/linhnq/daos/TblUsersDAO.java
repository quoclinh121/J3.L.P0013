/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import linhnq.utils.DBHelpers;
import linhnq.dtos.TblUsersDTO;

/**
 *
 * @author quocl
 */
public class TblUsersDAO implements Serializable {

    public TblUsersDTO checkLogin(String username, String password) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        TblUsersDTO dto = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT fullname, email, phone, roleID "
                        + "FROM tblUsers "
                        + "WHERE userID = ? AND password = ? AND status = 1";
                preStm = con.prepareStatement(sql);
                preStm.setString(1, username);
                preStm.setString(2, password);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    String fullname = rs.getNString("fullname");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String roleID = rs.getString("roleID");
                    dto = new TblUsersDTO(username, password, fullname, email, phone, true, roleID);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return dto;
    }

    public TblUsersDTO checkLoginGG(String userID) throws SQLException, NamingException {
        TblUsersDTO dto = null;
        Connection con = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT fullname, email, phone, roleID "
                        + "FROM tblUsers "
                        + "WHERE userID = ? AND status = 1";
                preStm = con.prepareStatement(sql);
                preStm.setString(1, userID);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    String fullname = rs.getNString("fullname");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String roleID = rs.getString("roleID");
                    dto = new TblUsersDTO(userID, "", fullname, email, phone, true, roleID);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return dto;
    }

    public void createUserGG(TblUsersDTO dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement preStm = null;
        try {
            con = DBHelpers.makeConnection();
            if(con != null) {
                String sql = "INSERT INTO tblUsers(userID, password, fullname, email, phone, status, roleID) "
                        + "VALUES(?, ?, ?, ?, ?, 1, ?)";
                preStm = con.prepareStatement(sql);
                preStm.setString(1, dto.getUserID());
                preStm.setString(2, dto.getPassword());
                preStm.setString(3, dto.getFullname());
                preStm.setString(4, dto.getEmail());
                preStm.setString(5, dto.getPhone());
                preStm.setString(6, dto.getRoleID());
                preStm.executeUpdate();
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
