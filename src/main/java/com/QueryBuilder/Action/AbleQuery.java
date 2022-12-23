package com.QueryBuilder.Action;

public interface AbleQuery {
    Object ExecuteQuery(Class<?> itemType);
    Object ExecuteQueryWithOutRelationship(Class<?> itemType);
    boolean ExecuteNonQuery();
}
