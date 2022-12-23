package com.Ulti;

import com.Annotation.ColumnInfo;
import com.Annotation.DataType;
import com.Connector.Connector;

import java.beans.IntrospectionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;

public abstract class Converter {
    public <T> T MapWithRelationship(Class<T> type, Connector cnn, ResultSet dr,Ulti ulti )
            throws SQLException, IllegalAccessException, IntrospectionException {
        T obj = CreateInstance(type);
        var properties = type.getDeclaredFields();

        for (Field property : properties) {
            Annotation[] annotations = property.getDeclaredAnnotations();
            // var attributes = property.GetCustomAttributes(false);
           var columnMapping = ulti.FirstOrDefault(annotations, ColumnInfo.class);
            if (columnMapping != null) {
                var mapsTo = ((ColumnInfo) columnMapping);
                // property.setString(obj, dr[mapsTo.name()]);
                try {
                    property.set(obj, dr.getString(mapsTo.name()));
                }catch (Exception e){}
            }
        }

        MapOneToMany(type, cnn, dr, obj,ulti);
        MapToOne(type, cnn, dr, obj,ulti);

        return obj;
    }

    protected abstract <T> void MapOneToMany(Class<T> type, Connector cnn, ResultSet dr, T obj,Ulti ulti)
            throws SQLException, IllegalAccessException, IntrospectionException;

    protected abstract <T> void MapToOne(Class<T> type, Connector cnn, ResultSet dr, T obj,Ulti ulti)
            throws SQLException, IllegalAccessException;


    public <T> T MapWithOutRelationship(Class<T> type, Connector cnn, ResultSet dr,Ulti ulti )
            throws SQLException, IllegalAccessException {
        T obj = CreateInstance(type);
        var properties = type.getDeclaredFields();

        for (Field property : properties) {
            Annotation[] annotations = property.getDeclaredAnnotations();
            // var attributes = property.GetCustomAttributes(false);

            var columnMapping = ulti.FirstOrDefault(annotations, ColumnInfo.class);
            if (columnMapping != null) {
                var mapsTo = ((ColumnInfo) columnMapping);
                // property.setString(obj, dr[mapsTo.name()]);
                String nameOfColumn = mapsTo.name();
                if(mapsTo.type()== DataType.NCHAR||mapsTo.type()==DataType.NVARCHAR) {
                    String value = dr.getString(nameOfColumn);
                    property.set(obj, value);
                }
                else if(mapsTo.type()==DataType.INT){
                    var value = dr.getInt(nameOfColumn);
                    property.set(obj, value);
                }
                else if(mapsTo.type()== DataType.BOOL){
                    var value = dr.getBoolean(nameOfColumn);
                    property.set(obj, value);
                }
                else if(mapsTo.type()==DataType.FLOAT){
                    var value = dr.getFloat(nameOfColumn);
                    property.set(obj, value);
                }
            }
        }

        return obj;
    }
    public <T> T CreateInstance(Class<T> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
