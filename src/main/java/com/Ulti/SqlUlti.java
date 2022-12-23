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
public class SqlUlti extends Ulti {
    public SqlUlti(){
        this.converter = new SqlConverter();
    }

    @Override
    protected <T> void MapOneToMany(Class<T> type, Connector cnn, ResultSet dr, T obj)
            throws SQLException, IllegalAccessException, IntrospectionException {
         this.converter.MapOneToMany(type,cnn,dr,obj,this);
    }

    @Override
    protected <T> void MapToOne(Class<T> type, Connector cnn, ResultSet dr, T obj)
            throws SQLException, IllegalAccessException {
        this.converter.MapToOne(type,cnn,dr,obj,this);

    }
}
