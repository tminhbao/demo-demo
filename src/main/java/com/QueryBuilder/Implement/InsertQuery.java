package com.QueryBuilder.Implement;

import com.Annotation.ColumnInfo;
import com.Annotation.DataType;
import com.Annotation.PrimaryKey;
import com.Connector.Connector;
import com.Ulti.SqlUlti;
import com.QueryBuilder.Query;

import java.util.HashMap;
import java.util.List;


public class InsertQuery<T> extends Query {
    public InsertQuery(Connector connector, Class<T> typeParameterClass, T insertedObject) {

        super(connector); // use SqlQuery's contructor

        SqlUlti mapper = new SqlUlti();
        String tableName = mapper.GetTableName(typeParameterClass);
        List<PrimaryKey> primaryKeys = mapper.GetPrimaryKeys(typeParameterClass);
        HashMap<ColumnInfo, Object> hashMapColumnNameValues = mapper.GetColumnValues(insertedObject);

        if (hashMapColumnNameValues.size() != 0) {
            StringBuilder strColumnName = new StringBuilder("");
            StringBuilder strValue = new StringBuilder("");

            for (ColumnInfo column : hashMapColumnNameValues.keySet()) {


                for (PrimaryKey primaryKey : primaryKeys) {

                    // case: the primary key has been attributed which is auto increment.
                    if (column.name().equals(primaryKey.name()) && primaryKey.autoId()) {
                        break;
                    }

                    if (column.type() == DataType.NCHAR || column.type() == DataType.NVARCHAR){
                        strColumnName.append(", " + column.name());
                        strValue.append(", N'" + hashMapColumnNameValues.get(column) + "'");

                    }
                    else if (column.type() == DataType.CHAR || column.type() == DataType.VARCHAR){
                        strColumnName.append(", " + column.name());
                        strValue.append(", '" + hashMapColumnNameValues.get(column) + "'");
                    }
                    else {
                        strColumnName.append(", " + column.name());
                        strValue.append(", " + hashMapColumnNameValues.get(column));
                    }
                }
            }

            if(!strColumnName.equals("") && !strValue.equals("")){

                // just clean strColumnName and strValue
                strColumnName.delete(0,2);
                strValue.delete(0,2);

                this.strQuery = new StringBuilder("INSERT INTO " + tableName);
                this.strQuery.append("("+ strColumnName+ ")");
                this.strQuery.append(" VALUES ("+ strValue+")");
            }

            System.out.println("Insert String Query: "+ this.strQuery);
        }
    }
}
