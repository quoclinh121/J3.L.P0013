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
import linhnq.utils.DBHelpers;

/**
 *
 * @author quocl
 */
public class TblCategoriesDAO implements Serializable{
    
    public List<String> getAllCategories() throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<String> result = null;
        try {
            conn = DBHelpers.makeConnection();
            if(conn != null) {
                String sql = "SELECT categoryName "
                        + "From tblCategories";
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                while(rs.next()) {
                    if(result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(rs.getString("categoryName"));
                }
            }
        } finally {
            if(rs != null) rs.close();
            if(preStm != null) preStm.close();
            if(conn != null) conn.close();
        }
        return result;
    }
}
