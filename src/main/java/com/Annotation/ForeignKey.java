package com.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ForeignKey {
   //string relastionshipId,strig name,string refereces
    public String relationshipId() default "";
    public String name() default "";
    public String references() default "";
    
}
