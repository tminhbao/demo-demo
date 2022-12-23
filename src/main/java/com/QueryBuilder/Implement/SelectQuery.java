package com.QueryBuilder.Implement;


import com.Annotation.ColumnInfo;
import com.Connector.Connector;
import com.Ulti.SqlUlti;
import com.QueryBuilder.Query;
import com.QueryBuilder.Action.AbleRunOrGroupBy;
import com.QueryBuilder.Action.AbleHaving;
import com.QueryBuilder.Action.AbleWhere;
import com.QueryBuilder.Action.AbleRun;

import java.util.List;

public class SelectQuery<T> extends Query implements AbleWhere<T>, AbleRun<T>, AbleRunOrGroupBy<T>, AbleHaving<T> {

    private SelectQuery(Connector connector, Class<T> typeParameterClass) {
        super(connector);
        this.strQuery = new StringBuilder();
        SqlUlti mapper = new SqlUlti();
        strQuery.append("SELECT ");

        // just get attribute - column's name of table
        for (ColumnInfo column : mapper.GetColumns(typeParameterClass)) {
            strQuery.append(column.name() + ", ");
        }



        // remove ',' in 2nd index from the end of strQuery.
        strQuery.deleteCharAt(strQuery.length() - 2);
        strQuery.append(" FROM " + mapper.GetTableName(typeParameterClass));
    }

    public static <T> AbleWhere create(Connector connector, Class<T> typeParameterClass) {

        // create a connection to execute select query
        return new SelectQuery(connector, typeParameterClass);

    }

    @Override
    public AbleRunOrGroupBy<T> where(String strCondition) {

        if(strCondition != null){
            this.strQuery.append(" WHERE " + strCondition);
        }
        return this;
    }

    @Override
    public AbleRunOrGroupBy<T> allRows() {
        return this;
    }

    @Override
    public AbleHaving<T> groupBy(String... columnNames) {

        this.strQuery.append(" GROUP BY ");

        for (String columnName : columnNames) {
            this.strQuery.append(columnName + ", ");
        }

        // remove ',' in 2nd index from the end of strQuery.
        this.strQuery.deleteCharAt(this.strQuery.length() - 2);
        return this;
    }

    @Override
    public AbleRun<T> having(String condition) {
        this.strQuery.append(" HAVING " + condition);
        return this;
    }

    @Override
    public List<T> run(Class<?> itemType) {
        System.out.println("Select String Query: "+ this.strQuery);
        try {
            return (List<T>) ExecuteQuery(itemType);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        //return null;
    }
}
