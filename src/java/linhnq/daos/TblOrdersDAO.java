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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import linhnq.dtos.TblOrdersDTO;
import linhnq.utils.DBHelpers;

/**
 *
 * @author quocl
 */
public class TblOrdersDAO implements Serializable {

    private Timestamp convertDate(Date date) {
        Timestamp sqlDate = new Timestamp(date.getTime());
        return sqlDate;
    }

    public boolean addNewOrders(String orderID, String userID, int total, String paymentMethod, Date date) throws SQLException, NamingException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement preStm = null;

        try {
            conn = DBHelpers.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblOrders(orderID, total_price, payment_date, payment_method, userID) "
                        + "VALUES(?, ?, ?, ?, ?)";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, orderID);
                preStm.setInt(2, total);
                preStm.setTimestamp(3, convertDate(date));
                preStm.setString(4, paymentMethod);
                preStm.setString(5, userID);
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

    public List<TblOrdersDTO> getOrders(String userID) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblOrdersDTO> list = null;

        try {
            conn = DBHelpers.makeConnection();
            if (conn != null) {
                String sql = "SELECT orderID, total_price, payment_date, payment_method "
                        + "FROM tblOrders "
                        + "WHERE userID = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, userID);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    String orderID = rs.getString("orderID");
                    int total_price = rs.getInt("total_price");
                    Date payment_date = rs.getDate("payment_date");
                    String payment_method = rs.getString("payment_method");
                    TblOrdersDTO dto = new TblOrdersDTO(orderID, total_price, payment_date, payment_method, userID);
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

    public List<TblOrdersDTO> searchOrder(String userID, String searchValue, Date fromDate, Date toDate) throws SQLException, NamingException {
        List<TblOrdersDTO> result = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            conn = DBHelpers.makeConnection();
            if (conn != null) {
                String sql = sql = "SELECT orderID, total_price, payment_date, payment_method "
                        + "FROM tblOrders "
                        + "WHERE userID = ? AND orderID LIKE ? AND payment_date <= ? AND payment_date >= ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, userID);
                preStm.setString(2, "%" + searchValue + "%");
                preStm.setTimestamp(3, convertDate(toDate));
                preStm.setTimestamp(4, convertDate(fromDate));
                rs = preStm.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    String orderID = rs.getString("orderID");
                    int total_price = rs.getInt("total_price");
                    Date payment_date = rs.getDate("payment_date");
                    String payment_method = rs.getString("payment_method");
                    TblOrdersDTO dto = new TblOrdersDTO(orderID, total_price, payment_date, payment_method, userID);
                    result.add(dto);
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
        return result;
    }
}
