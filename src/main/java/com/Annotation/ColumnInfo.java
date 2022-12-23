package com.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnInfo {
    public String name() default "";
    public boolean nullable() default false;
    public int length() default 255;
    public DataType type() default DataType.NVARCHAR;
}
