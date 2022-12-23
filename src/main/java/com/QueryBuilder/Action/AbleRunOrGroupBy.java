package com.QueryBuilder.Action;

import java.util.List;

public interface AbleRunOrGroupBy<T>{

    AbleHaving<T> groupBy(String ...columnNames);

    List<T> run(Class<?> itemType);
}
