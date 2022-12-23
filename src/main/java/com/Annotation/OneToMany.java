package com.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OneToMany {
   //string relationshipId,string table name
    public String relationshipId() default "";
    public String tableName() default "";
    public Class<?> itemType() default Object.class;
    //itemType is the class in array, for ex: in class Techer{
    //    @OneToMany(itemType = Student.class) // teacher has many student
    //    ArrayList<Student> students;
    // }
}
