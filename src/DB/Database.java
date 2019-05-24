/*
 * Copyright (c) 2019. Dušan Stančík
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author
 */
public class Database {
   //připojení k databázi 
   Connection connection;
   //instance třídy Query
   Query query;
   
    /**
     * Konstruktor třídy Database
     * @param db
     * @param userName
     * @param password
     * @throws SQLException
     */
    public Database(String db, String userName, String password) throws SQLException{
        connection = DriverManager.getConnection("jdbc:mysql://46.229.230.118/" +db, userName, password);
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Metoda odešle sestavená sql dotaz
     * @param query
     * @param params
     * @return 
     * @throws SQLException
     */


    private int query(String query, Object[] params) throws SQLException{
         PreparedStatement ps = connection.prepareStatement(query);
         if(params != null){   
           int index = 1;
           for(Object param : params){ 
             ps.setObject(index, param);
            index++;
           }
         }  
         return ps.executeUpdate();
    }
    
    /**
     * Vymaže údaje z databázové tabulky
     * @param table
     * @param requirement
     * @param param
     * @return
     * @throws SQLException
     */
    public int delete(String table, String requirement, Object[] param) throws SQLException{
        query = new Query();
        query.delete(table)
             .where(requirement);
        return query(query.getQuery(), param);
    }
    
     /**
     * Uloží do databázové tabulky 1 subjekt
     * @param table
     * @param params
     * @return 
     * @throws java.sql.SQLException
     */
    public int insert(String table, Object[] params) throws SQLException{
        query = new Query();
        query.insert(table)
             .values(params);
        return query(query.getQuery(), params);
    }


    public int insertcolumn(String table, String[] columns,Object[] params) throws SQLException{
        query = new Query();
        query.insert(table)
                .setcolumn(columns)
                .values(params);
        return query(query.getQuery(), params);
    }



    /**
     * Aktualizuje data uložená v databázové tabulce
     * @param table
     * @param columns
     * @param requirement
     * @param params
     * @return
     * @throws SQLException
     */
    public int update(String table, String[] columns, String requirement, Object[] params) throws SQLException{
        query = new Query();
        
        query.update(table)
             .set(columns)
             .where(requirement); 
        
        return query(query.getQuery(), params);
    }
    
    /**
     * Vrací údaje z databázové tabulky
     * @param table
     * @param columns
     * @param params
     * @return
     * @throws SQLException
     */
    public ResultSet select(String table, Object[] columns, Object[] params) throws SQLException{
        return this.select(table, columns, "", params);
    }
    
    /**
     * Vrací údaje z databázové tabulky
     * @param table
     * @param columns
     * @param requirement
     * @param params
     * @return
     * @throws SQLException
     */
    public ResultSet select(String table, Object[] columns, String requirement, Object[] params) throws SQLException{
        query = new Query();
        query.select(columns);
        query.from(table);
            if(params != null) {
             query.where(requirement);
            }
        PreparedStatement ps = connection.prepareStatement(query.getQuery());
        if(params != null){
            int index = 1;
            for(Object param : params){
            ps.setObject(index, param);
            index++;
         } 
        }
        
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    
    /**
     *
     * @param table
     * @return
     * @throws SQLException
     */
    public int count(String table) throws SQLException{
        PreparedStatement ps =
                connection.prepareStatement("SELECT COUNT(*) FROM "+table);
       ResultSet result = ps.executeQuery();
         result.next();
        return result.getInt(1);
    }
}
