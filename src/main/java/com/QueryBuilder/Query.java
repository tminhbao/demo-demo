package com.QueryBuilder;

import com.Connector.*;
import com.QueryBuilder.Action.AbleQuery;
import com.Ulti.SqlUlti;

import java.beans.IntrospectionException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Query implements AbleQuery {
    protected Connector connector;
    protected StringBuilder strQuery;

    public Query(Connector connector) {
        this.connector = connector;
    }
    public Query(Connector connector, String query) {
        this.connector = connector;
        this.strQuery = new StringBuilder(query);
    }

    @Override
    public Object ExecuteQuery(Class<?> itemType) {
        try {
            if(connector.connection.isClosed()){connector.Open();}
            PreparedStatement statement = connector.connection.prepareStatement(strQuery.toString());
            ResultSet resultSet = statement.executeQuery();

            ArrayList<Object> list = new ArrayList<>();
            var sqlMapper = new SqlUlti();
            while (resultSet.next()){
                var obj =   sqlMapper.MapWithRelationship(itemType,this.connector, resultSet);
                list.add(obj);
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object ExecuteQueryWithOutRelationship(Class<?> itemType) {
        try {
            PreparedStatement statement = connector.connection.prepareStatement(strQuery.toString());
            ResultSet resultSet = statement.executeQuery();

            ArrayList<Object> list = new ArrayList<>();
            var sqlMapper = new SqlUlti();
            while (resultSet.next()){
                var obj =   sqlMapper.MapWithOutRelationship(itemType,this.connector, resultSet);
                list.add(obj);
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean ExecuteNonQuery() {
        try {
            String queryStatement = strQuery.toString();
            PreparedStatement statement = connector.connection.prepareStatement(queryStatement);
            return statement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

