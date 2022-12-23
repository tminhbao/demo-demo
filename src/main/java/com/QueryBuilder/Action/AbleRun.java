package com.QueryBuilder.Action;

import java.util.List;

public interface AbleRun<T> {

    List<T> run(Class<?> itemType);
}
