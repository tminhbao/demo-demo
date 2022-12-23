package com.Ulti;

import java.beans.IntrospectionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;


import com.Annotation.*;
import com.Connector.Connector;

public abstract class Ulti {
    public Converter converter;

    public <T> T MapWithRelationship(Class<T> type, Connector cnn, ResultSet dr)
            throws SQLException, IllegalAccessException, IntrospectionException {
        return converter.MapWithRelationship(type,cnn,dr,this);
    }

    protected abstract <T> void MapOneToMany(Class<T> type, Connector cnn, ResultSet dr, T obj)
            throws SQLException, IllegalAccessException, IntrospectionException;

    protected abstract <T> void MapToOne(Class<T> type, Connector cnn, ResultSet dr, T obj)
            throws SQLException, IllegalAccessException;


    public <T> T MapWithOutRelationship(Class<T> type, Connector cnn, ResultSet dr)
            throws SQLException, IllegalAccessException {
        return converter.MapWithOutRelationship(type,cnn,dr,this);
    }

    public <T> String GetTableName(Class<T> type) {
        Annotation[] tableAttributes = type.getDeclaredAnnotations();
        // var tableAttributes = typeof(T).GetCustomAttributes(typeof(TableAttribute),
        // true);
        var tableAttribute = FirstOrDefault(tableAttributes, Table.class);
        // var tableAttribute = FirstOrDefault(tableAttributes, typeof(TableAttribute))
        // as TableAttribute;
        if (tableAttribute != null)
            return ((Table) tableAttribute).name();
        return "";
    }

    public <T> ArrayList<PrimaryKey> GetPrimaryKeys(Class<T> type) {
        ArrayList<PrimaryKey> primaryKeys = new ArrayList<PrimaryKey>();

        var properties = type.getDeclaredFields();
        for (Field property : properties) {
            Annotation[] attributes = property.getDeclaredAnnotations();
            // var attributes = property.GetCustomAttributes(false);
            var primaryKeyAttribute = FirstOrDefault(attributes, PrimaryKey.class);
            if (primaryKeyAttribute != null)
                primaryKeys.add((PrimaryKey) primaryKeyAttribute);
        }

        if (primaryKeys.size() > 0)
            return primaryKeys;
        else
            return null;
    }

    public <T> ArrayList<ForeignKey> GetForeignKeys(Class<T> type, String relationshipID) {
        ArrayList<ForeignKey> foreignKeys = new ArrayList<ForeignKey>();

        var properties = type.getDeclaredFields();
        for (Field property : properties) {
            Annotation[] attributes = property.getDeclaredAnnotations();
            // var attributes = property.GetCustomAttributes(false);
            var foreignKeyAttribute = FirstOrDefault(attributes, ForeignKey.class);
            ForeignKey foreignKeyhere = ((ForeignKey) foreignKeyAttribute);
            if (foreignKeyhere != null) {
                var relationShipIdOfThis = ((ForeignKey) foreignKeyAttribute).relationshipId();
                var equal = relationShipIdOfThis.equals(relationshipID);// == relationshipID;
                if (equal) {
                    foreignKeys.add((ForeignKey) foreignKeyAttribute);
                }
            }
        }

        if (foreignKeys.size() > 0)
            return foreignKeys;
        else
            return null;
    }

    public <T> ArrayList<ColumnInfo> GetColumns(Class<T> type) {
        ArrayList<ColumnInfo> columnAttributes = new ArrayList<ColumnInfo>();
        var properties = type.getDeclaredFields();
        for (Field property : properties) {
            Annotation[] attributes = property.getDeclaredAnnotations();
            // var attributes = property.GetCustomAttributes(false);
            var columnMapping = FirstOrDefault(attributes, ColumnInfo.class);

            if (columnMapping != null) {
                var mapsTo = (ColumnInfo) columnMapping;
                columnAttributes.add(mapsTo);
            }
        }

        if (columnAttributes.size() > 0)
            return columnAttributes;
        else
            return null;
    }


    public <T> HashMap<ColumnInfo, Object> GetColumnValues(T obj) {
        HashMap<ColumnInfo, Object> listColumnValues = new HashMap<ColumnInfo, Object>();
        var properties = obj.getClass().getDeclaredFields();
        for (Field property : properties) {
            Annotation[] attributes = property.getDeclaredAnnotations();
            // var attributes = property.GetCustomAttributes(false);
            var columnMapping = FirstOrDefault(attributes, ColumnInfo.class);

            if (columnMapping != null) {
                var mapsTo = (ColumnInfo) columnMapping;
                try {
                    listColumnValues.put(mapsTo, property.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        if (listColumnValues.size() > 0)
            return listColumnValues;
        else
            return null;
    }


    public ColumnInfo FindColumn(String name, HashMap<ColumnInfo, Object> listColumValues) {
        for (ColumnInfo column : listColumValues.keySet()) {
            String columnName = column.name();
            if (columnName.equals(name))
                return column;
        }
        return null;
    }


    public ColumnInfo FindColumn(String name, ArrayList<ColumnInfo> listColumAttributes) {
        for (ColumnInfo column : listColumAttributes)
            if (column.name().equals(name))
                return column;
        return null;
    }

    protected <T> T FirstOrDefault(Annotation[] attributes, Class<?> type) {
        for (Annotation a : attributes) {

            /* check if a is class of type then return a */
            var isTrue = a.annotationType().equals(type);
            // var b =CreateInstance(type);
            // var bClass = b.getClass();
            if (isTrue)
                return (T) a;

        }
        return null;
    }


    public <T> T[] GetAll(Annotation[] attributes, Class<T> type) {
        T[] objArray = (T[]) Array.newInstance(type, 0);
        for (Annotation a : attributes) {
            if (a.annotationType().equals(type)) {
                objArray = Arrays.copyOf(objArray, objArray.length + 1);
                objArray[objArray.length - 1] = (T) a;
            }
        }
        return objArray;
    }

    public <T> T GetFirst(Iterable<T> source) {
        Iterator<T> iter = source.iterator();

        if (iter.hasNext()) {
            return iter.next();
        }
        return null;
    }


}
