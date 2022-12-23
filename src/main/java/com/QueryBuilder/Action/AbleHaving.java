package com.QueryBuilder.Action;


import java.util.List;

public interface AbleHaving<T> {
    AbleRun<T> having(String condition);

    List<T> run(Class<?> itemType);

}
