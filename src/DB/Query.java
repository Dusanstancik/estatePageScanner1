/*
 * Copyright (c) 2019. Dušan Stančík
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DB;

/**
 *
 * @author
 */
public class Query {
    private StringBuilder query;
    
    /**
     *
     * @param table
     * @return
     */
    public Query delete(String table){
        query = new StringBuilder();
        query.append("DELETE FROM ");
        query.append(table);
        return this;
    }
    
    /**
     * 
     * @param table
     * @return
     */
    public Query update(String table){
        query = new StringBuilder();
        query.append("UPDATE ");
        query.append(table);
        query.append(" SET ");
        return this;
    }
    
    /**
     * Doplní sloupce
     * @param Columns
     */
    public Query set(String[] columns){
        int count = columns.length;
        if(count == 0)
            throw new IllegalArgumentException("Neplatný počet parametrů");
        
        for(String column : columns){
           query.append(column); 
           query.append(" = ");
           query.append("?");
           query.append(",");
        }
        query.deleteCharAt(query.lastIndexOf(","));
        return this;
    }

    public Query setcolumn(String[] columns){
        int count = columns.length;
        if(count == 0)
            throw new IllegalArgumentException("Neplatný počet parametrů");
        query.append("(");
        for(String column : columns){
            query.append(column);
            query.append(",");
        }
        query.deleteCharAt(query.lastIndexOf(","));
        query.append(")");
        return this;
    }



    /**
     * 
     * @param table
     * @return
     */
    public Query insert(String table){
        query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append(table);
        return this;
    }
    
    /**
     *
     * @param params
     * @return
     */
    public Query values(Object[] params){
        query.append(" VALUES(");
        
        int count = params.length;
        
        if(count == 0)
            throw new IllegalArgumentException("Neplatný počet parametrů");
      
        for (int i = 0; i<count; i++) {
            query.append("?,");
        }
        //odstaníme poslední čárku
        query.deleteCharAt(query.lastIndexOf(","));
        query.append(");");
        return this;
    }
    
    /**
     *
     * @param columns
     * @return
     */
    public Query select(Object[] columns){
        query = new StringBuilder();
        query.append("SELECT ");
        if(columns != null){
            for(Object column : columns){
                query.append(column);
                query.append(",");
            }
            //odstaníme poslední otazník
            query.deleteCharAt(query.lastIndexOf(","));
        }
        else
            query.append("*");
        
        return this;
    }
    
    /**
     *
     * @param table
     * @return
     */
    public Query from(String table){
        query.append(" FROM ");
        query.append(table);
        return this;
    }
    
    /**
     * Přidá podmínku do sql dotazu
     * @param requirement
     * @return
     */
    public Query where(String requirement){
        query.append(" WHERE ");
        query.append(requirement);
        return this;
    }
    
     /**
     * Vrátí sestavený sql dotaz
     * @return query
     */
    public String getQuery(){
        return query.toString();
    }
}
