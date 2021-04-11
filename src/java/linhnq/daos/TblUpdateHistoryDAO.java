/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.naming.NamingException;
import linhnq.utils.DBHelpers;

/**
 *
 * @author quocl
 */
public class TblUpdateHistoryDAO implements Serializable {
    
    private Timestamp convertDate(Date date) {
        Timestamp sqlDate = new Timestamp(date.getTime());
        return sqlDate;
    }

    public void addNewUpdate(String updateID, String itemID, String userID, String action, Date updateDate) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        try {
            conn = DBHelpers.makeConnection();
            if(conn != null) {
                String sql = "INSERT INTO tblUpdateHistory(updateID, itemID, userID, action, updateDate) "
                        + "VALUES(?, ?, ?, ?, ?)";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, updateID);
                preStm.setString(2, itemID);
                preStm.setString(3, userID);
                preStm.setString(4, action);
                preStm.setTimestamp(5, convertDate(updateDate));
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
