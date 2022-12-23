package com.Ulti;

import com.Annotation.*;
import com.Connector.Connector;

import java.beans.IntrospectionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlConverter extends Converter {
    @Override
    protected <T> void MapOneToMany(Class<T> type, Connector cnn, ResultSet dr, T obj,Ulti ulti)
            throws SQLException, IllegalAccessException, IntrospectionException {
        Field[] properties = type.getFields();

        for (Field property : properties) {
            Annotation[] annotations = property.getAnnotations();

            OneToMany[] oneToManies = ulti.GetAll(annotations, OneToMany.class);
            if (oneToManies != null && oneToManies.length != 0) {
                for (OneToMany oneToMany : oneToManies) {
                    Class<?> propertyType = property.getType();
                    if (propertyType.isAssignableFrom(ArrayList.class)) {
                        var itemType = (Class<?>) oneToMany.itemType();
                        SqlUlti mapper = new SqlUlti();

                        String tableName = mapper.GetTableName(itemType);

                        List<ForeignKey> foreignKeys = mapper.GetForeignKeys(itemType,
                                oneToMany.relationshipId());

                        List<ColumnInfo> columnInfos = mapper.GetColumns(type);

                        String whereStr = "";
                        if (foreignKeys != null) {
                            for (ForeignKey foreignKey : foreignKeys) {
                                ColumnInfo column = ulti.FindColumn(foreignKey.references(),
                                        (ArrayList) columnInfos);
                                var name = foreignKey.name();
                                var drObject = dr.getObject(foreignKey.references());
                                if (column != null) {

                                    if (column.type() == DataType.NCHAR || column.type() == DataType.NVARCHAR) {
                                        // format = "{0} = N'{1}', ";
                                        whereStr += name + " = N'" + drObject;
                                    } else if (column.type() == DataType.CHAR || column.type() == DataType.VARCHAR) {
                                        // format = "{0} = '{1}', ";
                                        whereStr += name + " = " + drObject;

                                    }
                                }
                            }
                        }
                        if (!whereStr.isEmpty()) {
                            String query = String.format("SELECT * FROM %s WHERE %s", tableName, whereStr);

                            var listToWrite = cnn.ExecuteQueryWithOutRelationship(itemType, query);
                            property.set(obj, listToWrite);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected <T> void MapToOne(Class<T> type, Connector cnn, ResultSet dr, T obj,Ulti ulti)
            throws SQLException, IllegalAccessException {
        var properties = type.getDeclaredFields();

        for (var property : properties) {
            var propertyType = property.getType();
            var annotations = property.getAnnotations();

            var arr1 = ulti.GetAll(annotations, OneToOne.class);
            var arr2 = ulti.GetAll(annotations, ManyToOne.class);

            Annotation[] annotations1 = new Annotation[arr1.length+arr2.length];
            if (arr1.length+arr2.length > 0) {
                System.arraycopy(arr1, 0, annotations1, 0, arr1.length);
                System.arraycopy(arr2, 0, annotations1, arr1.length, arr2.length);
            }

            if (annotations1 != null && annotations1.length!= 0) {
                for (Annotation annotation : annotations1) {
                    SqlUlti mapper = new SqlUlti();
                    String tableName = "";
                    String whereStr = "";
                    String relationshipID = "";

                    if (annotation.annotationType().equals(OneToOne.class)) {
                        relationshipID = ((OneToOne) annotation).relationshipId();
                        tableName = ((OneToOne) annotation).tableName();
                    } else {
                        relationshipID = ((ManyToOne) annotation).relationshipId();
                        tableName = ((ManyToOne) annotation).tableName();
                    }

                    var foreignKeys = mapper.GetForeignKeys(type, relationshipID);

                    var columnInfoArrayList = mapper.GetColumns(propertyType);

                    if (foreignKeys != null) {
                        try {
                            for (ForeignKey foreignKey : foreignKeys) {
                                ColumnInfo column = ulti.FindColumn(foreignKey.references(),
                                        (ArrayList) columnInfoArrayList);
                                if (column != null) {
                                    String references = foreignKey.references();
                                    String drString = (String) dr.getString(foreignKey.name());
                                    //String format = "{0} = {1}, ";
                                    if (column.type() == DataType.NCHAR || column.type() == DataType.NVARCHAR) {
                                        //  format = "{0} = N'{1}', ";
                                        whereStr += references + " = N'" + drString;
                                    } else if (column.type() == DataType.CHAR || column.type() == DataType.VARCHAR) {
                                        //format = "{0} = '{1}', ";
                                        whereStr += references + " = " + drString;
                                    } else if (column.type() == DataType.INT) {
                                        whereStr += references + " = " + drString;
                                    }
                                }
                            }
                        }catch (Exception e){
                            //e.printStackTrace();
                        }
                    }
                    if (!whereStr.isEmpty()) {
                        String query = String.format("SELECT * FROM %s WHERE %s", tableName, whereStr);

                        var ienumerable = cnn.ExecuteQueryWithOutRelationship(propertyType, query);

                        try {
                            var firstElement = ((ArrayList) ienumerable).get(0);
                            property.set(obj, firstElement);
                        }catch (Exception e) {
                            //e.printStackTrace();
                        }

                    }
                }
            }
        }

    }
}
