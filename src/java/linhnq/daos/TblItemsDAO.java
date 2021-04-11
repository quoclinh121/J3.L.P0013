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
import linhnq.dtos.TblItemsDTO;
import linhnq.utils.DBHelpers;

/**
 *
 * @author quocl
 */
public class TblItemsDAO implements Serializable {

    public List<TblItemsDTO> getItems(int pageSize, int pageNumber, String role) throws SQLException, NamingException {
        List<TblItemsDTO> result = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.makeConnection();
            if (conn != null) {
                String sql = null;
                if ("ad".equals(role)) {
                    sql = "SELECT itemID, itemName, quantity, price, createDate, description, image, categoryName, status "
                            + "FROM tblItems a, tblCategories b "
                            + "WHERE a.categoryID = b.categoryID "
                            + "ORDER BY createDate DESC "
                            + "OFFSET ? * (? - 1) ROWS "
                            + "FETCH NEXT ? ROWS ONLY";
                } else {
                    sql = "SELECT itemID, itemName, quantity, price, createDate, description, image, categoryName, status "
                            + "FROM tblItems a, tblCategories b "
                            + "WHERE a.categoryID = b.categoryID AND status = 1 AND quantity > 0 "
                            + "ORDER BY createDate DESC "
                            + "OFFSET ? * (? - 1) ROWS "
                            + "FETCH NEXT ? ROWS ONLY";
                }

                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, pageSize);
                preStm.setInt(2, pageNumber);
                preStm.setInt(3, pageSize);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String itemID = rs.getString("itemID");
                    String itemName = rs.getString("itemName");
                    int quantity = rs.getInt("quantity");
                    int price = rs.getInt("price");
                    Date createDate = rs.getDate("createDate");
                    String description = rs.getString("description");
                    String image = rs.getString("image");
                    String category = rs.getString("categoryName");
                    boolean status = rs.getBoolean("status");
                    TblItemsDTO dto = new TblItemsDTO(itemID, itemName, quantity, price, createDate, description, image, category, status);
                    if (result == null) {
                        result = new ArrayList<>();
                    }
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

    public int getNoOfRecord(String role) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        int record = 0;
        try {
            conn = DBHelpers.makeConnection();
            if(conn != null) {
                String sql = "";
                if("ad".equals(role)) {
                    sql = "SELECT COUNT(itemID) AS noOfRecord "
                            + "FROM tblItems";
                } else {
                    sql = "SELECT COUNT(itemID) AS noOfRecord "
                            + "FROM tblItems "
                            + "WHERE status = 1 AND quantity > 0";
                }
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                if(rs.next()) {
                    record = rs.getInt("noOfRecord");
                }
            }
        } finally {
            if(rs != null) rs.close();
            if(preStm != null) preStm.close();
            if(conn != null) conn.close();
        }
        return record;
    }
    
    public List<TblItemsDTO> searchItems(String searchValue, int rangePrice, String category, int pageNumber, int pageSize, String role)
            throws SQLException, NamingException {
        List<TblItemsDTO> result = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.makeConnection();
            if (conn != null) {
                String sql = null;
                if ("ad".equals(role)) {
                    sql = "SELECT itemID, itemName, quantity, price, createDate, description, image, categoryName, status "
                            + "FROM tblItems a, tblCategories b "
                            + "WHERE a.categoryID = b.categoryID AND itemName LIKE ? AND price <= ? AND categoryName LIKE ? "
                            + "ORDER BY createDate DESC "
                            + "OFFSET ? * (? - 1) ROWS "
                            + "FETCH NEXT ? ROWS ONLY";
                } else {
                    sql = "SELECT itemID, itemName, quantity, price, createDate, description, image, categoryName, status "
                            + "FROM tblItems a, tblCategories b "
                            + "WHERE a.categoryID = b.categoryID AND itemName LIKE ? AND status = 1 AND price <= ? AND quantity > 0 AND categoryName LIKE ? "
                            + "ORDER BY createDate DESC "
                            + "OFFSET ? * (? - 1) ROWS "
                            + "FETCH NEXT ? ROWS ONLY";
                }

                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + searchValue + "%");
                preStm.setInt(2, rangePrice);
                preStm.setString(3, "%" + category + "%");
                preStm.setInt(4, pageSize);
                preStm.setInt(5, pageNumber);
                preStm.setInt(6, pageSize);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String itemID = rs.getString("itemID");
                    String itemName = rs.getString("itemName");
                    int quantity = rs.getInt("quantity");
                    int price = rs.getInt("price");
                    Date createDate = rs.getDate("createDate");
                    String description = rs.getString("description");
                    String image = rs.getString("image");
                    String categoryName = rs.getString("categoryName");
                    boolean status = rs.getBoolean("status");
                    TblItemsDTO dto = new TblItemsDTO(itemID, itemName, quantity, price, createDate, description, image, categoryName, status);

                    if (result == null) {
                        result = new ArrayList<>();
                    }
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

    public int getNoOfRecordForSearch(String searchValue, int rangePrice, String category, String role) throws SQLException, NamingException {
        int result = 0;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.makeConnection();
            if (conn != null) {
                String sql = null;
                if ("ad".equals(role)) {
                    sql = "SELECT COUNT(itemID) AS noOfRecord "
                            + "FROM tblItems a, tblCategories b "
                            + "WHERE a.categoryID = b.categoryID AND itemName LIKE ? AND price <= ? AND categoryName LIKE ? ";
                } else {
                    sql = "SELECT COUNT(itemID) AS noOfRecord "
                            + "FROM tblItems a, tblCategories b "
                            + "WHERE a.categoryID = b.categoryID AND itemName LIKE ? AND status = 1 AND price <= ? AND quantity > 0 AND categoryName LIKE ? ";
                }

                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + searchValue + "%");
                preStm.setInt(2, rangePrice);
                preStm.setString(3, "%" + category + "%");
                rs = preStm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("noOfRecord");
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

    public boolean deleteItem(String itemID) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        boolean result = false;
        try {
            conn = DBHelpers.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblItems "
                        + "SET status = 0 "
                        + "WHERE itemID = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, itemID);
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

    public TblItemsDTO getItem(String itemID) throws SQLException, NamingException {
        TblItemsDTO dto = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            conn = DBHelpers.makeConnection();
            if (conn != null) {
                String sql = "SELECT itemName, quantity, price, createDate, description, image, categoryName, status "
                        + "FROM tblItems a, tblCategories b "
                        + "WHERE a.categoryID = b.categoryID AND itemID = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, itemID);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    String itemName = rs.getString("itemName");
                    int quantity = rs.getInt("quantity");
                    int price = rs.getInt("price");
                    Date createDate = rs.getDate("createDate");
                    String description = rs.getString("description");
                    String image = rs.getString("image");
                    String categoryName = rs.getString("categoryName");
                    boolean status = rs.getBoolean("status");
                    dto = new TblItemsDTO(itemID, itemName, quantity, price, createDate, description, image, categoryName, status);
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
        return dto;
    }

    public boolean updateItem(TblItemsDTO dto) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        boolean result = false;

        try {
            conn = DBHelpers.makeConnection();
            if(conn != null) {
                String sql = "UPDATE tblItems "
                        + "SET itemName = ?, quantity = ?, price = ?, description = ?, image = ?, status = ?, categoryID = (SELECT categoryID "
                                                                                                                        + "FROM tblCategories " 
                                                                                                                        + "WHERE categoryName = ?) "
                        + "WHERE itemID = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setNString(1, dto.getItemName());
                preStm.setInt(2, dto.getQuantity());
                preStm.setInt(3, dto.getPrice());
                preStm.setNString(4, dto.getDescription());
                preStm.setString(5, dto.getImage());
                preStm.setBoolean(6, dto.isStatus());
                preStm.setString(7, dto.getCategoryID());
                preStm.setString(8, dto.getItemID());
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
    
    private Timestamp convertDate(Date date) {
        Timestamp sqlDate = new Timestamp(date.getTime());
        return sqlDate;
    }
    
    public boolean addNewItem(TblItemsDTO dto) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        boolean result = false;
        try {
            conn = DBHelpers.makeConnection();
            if(conn != null) {
                String sql = "INSERT INTO tblItems(itemID, itemName, quantity, price, createDate, description, image, categoryID, status) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, (SELECT categoryID FROM tblCategories WHERE categoryName = ?), ?)";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, dto.getItemID());
                preStm.setNString(2, dto.getItemName());
                preStm.setInt(3, dto.getQuantity());
                preStm.setInt(4, dto.getPrice());
                preStm.setTimestamp(5, convertDate(dto.getCreateDate()));
                preStm.setNString(6, dto.getDescription());
                preStm.setString(7, dto.getImage());
                preStm.setString(8, dto.getCategoryID());
                preStm.setBoolean(9, dto.isStatus());
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
    
    public int getQuantityOfItem(String itemID) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        int quantity = 0;
        
        try {
            conn = DBHelpers.makeConnection();
            if(conn != null) {
                String sql = "SELECT quantity "
                        + "FROM tblItems "
                        + "WHERE itemID = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, itemID);
                rs = preStm.executeQuery();
                if(rs.next()) {
                    quantity = rs.getInt("quantity");
                }
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return quantity;
    }
    
    public void updateQuantityItemInStock(String itemID, int quantitySold) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        
        try {
            conn = DBHelpers.makeConnection();
            if(conn != null) {
                String sql = "UPDATE tblItems "
                        + "SET quantity = (SELECT quantity FROM tblItems WHERE itemID = ?) - ? "
                        + "WHERE itemID = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, itemID);
                preStm.setInt(2, quantitySold);
                preStm.setString(3, itemID);
                preStm.executeUpdate();
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
