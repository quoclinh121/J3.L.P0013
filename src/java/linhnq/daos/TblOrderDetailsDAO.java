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
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import linhnq.dtos.TblOrderDetailDTO;
import linhnq.utils.DBHelpers;

/**
 *
 * @author quocl
 */
public class TblOrderDetailsDAO implements Serializable {

    public boolean addDetailOrder(String orderID, String itemID, int quantity) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        boolean result = false;

        try {
            conn = DBHelpers.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblOrderDetails(orderID, itemID, quantity) "
                        + "VALUES(?, ?, ?)";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, orderID);
                preStm.setString(2, itemID);
                preStm.setInt(3, quantity);
                result = preStm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public List<TblOrderDetailDTO> getItemsInOrder(String orderID) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblOrderDetailDTO> list = null;

        try {
            conn = DBHelpers.makeConnection();
            if (conn != null) {
                String sql = "SELECT itemName, a.quantity "
                        + "FROM tblOrderDetails a, tblItems b "
                        + "WHERE a.itemID = b.itemID AND orderID = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, orderID);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String itemName = rs.getString("itemName");
                    int quantity = rs.getInt("quantity");
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    TblOrderDetailDTO dto = new TblOrderDetailDTO(orderID, itemName, quantity);
                    list.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

}
