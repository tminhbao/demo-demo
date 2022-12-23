package com.QueryBuilder.Implement;

import com.Annotation.ColumnInfo;
import com.Annotation.DataType;
import com.Annotation.PrimaryKey;
import com.Connector.*;
import com.Ulti.SqlUlti;
import com.QueryBuilder.Query;

import java.util.HashMap;
import java.util.List;

public class UpdateQuery<T> extends Query {


    public UpdateQuery(Connector connector, Class<T> typeParameterClass, T updatedObject) {
        super(connector);

        SqlUlti mapper = new SqlUlti();
        String tableName = mapper.GetTableName(typeParameterClass);
        List<PrimaryKey> primaryKeys = mapper.GetPrimaryKeys(typeParameterClass);
        HashMap<ColumnInfo, Object> hashMapColumnNameValues = mapper.GetColumnValues(updatedObject);

        if(hashMapColumnNameValues != null && primaryKeys != null){

            StringBuilder strNewValue = new StringBuilder("");
            StringBuilder strCondition = new StringBuilder("");

            // Handle logic update value
            for(ColumnInfo columnInfo:hashMapColumnNameValues.keySet()){

                if(isPrimaryKey(primaryKeys,columnInfo)){
                   continue;
                }

                if (columnInfo.type() == DataType.NCHAR || columnInfo.type() == DataType.NVARCHAR){
                    strNewValue.append(", " + columnInfo.name()+ " = " +" N'" + hashMapColumnNameValues.get(columnInfo) + "'");
                }
                else if (columnInfo.type() == DataType.CHAR || columnInfo.type() == DataType.VARCHAR){
                    strNewValue.append(", " + columnInfo.name()+ " = " +" '" + hashMapColumnNameValues.get(columnInfo) + "'");
                }
                else {
                    strNewValue.append(", " + columnInfo.name()+ " = "  + hashMapColumnNameValues.get(columnInfo));
                }
            }

            // Handle logic condition - where statement
            for(PrimaryKey primaryKey:primaryKeys){

                ColumnInfo columnInfo = mapper.FindColumn(primaryKey.name(), hashMapColumnNameValues);

                if(columnInfo != null){

                    if (columnInfo.type() == DataType.NCHAR || columnInfo.type() == DataType.NVARCHAR){
                        strCondition.append(", " + columnInfo.name()+ " = " +" N'" + hashMapColumnNameValues.get(columnInfo) + "'");
                    }
                    else if (columnInfo.type() == DataType.CHAR || columnInfo.type() == DataType.VARCHAR){
                        strCondition.append(", " + columnInfo.name()+ " = " +" '" + hashMapColumnNameValues.get(columnInfo) + "'");
                    }
                    else {
                        strCondition.append(", " + columnInfo.name()+ " = "  + hashMapColumnNameValues.get(columnInfo));
                    }
                }

                if(!strCondition.equals("") && !strNewValue.equals("")){

                    // clean 'redundant' from strCondition and strNewValue
                    strCondition.delete(0,2);
                    strNewValue.delete(0,2);

                    this.strQuery = new StringBuilder("UPDATE " + tableName);
                    this.strQuery.append(" SET " + strNewValue);
                    this.strQuery.append(" WHERE "+ strCondition);

                    System.out.println("Update String Query: "+ this.strQuery);
                }
            }
        }
    }

    public boolean isPrimaryKey(List<PrimaryKey> primaryKeys, ColumnInfo columnInfo){

        for(PrimaryKey primaryKey:primaryKeys){

            if(columnInfo.name().equals(primaryKey.name())){
                return true;
            }
        }
        return false;
    }
}
