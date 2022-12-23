package com.Repository;

import com.Connector.Connector;
import com.Connector.ConnectorFactory.MySqlConnectorFactory;
import com.Example.Account;
import com.Example.CreditCard;
import com.Example.User;
import com.QueryBuilder.Implement.SelectQuery;

public class CreditCardRepository implements Repository{
    @Override
    public Object SelectAll() {
        Connector connector = MySqlConnectorFactory.createConnector();
        connector.Open();
        Object result =  SelectQuery.create(connector, CreditCard.class).allRows().run(CreditCard.class);
        connector.Close();
        return result;
    }
    @Override
    public Object SelectWhere(String whereClause) {
        Connector connector = MySqlConnectorFactory.createConnector();
        connector.Open();
        Object result = SelectQuery.create(connector, CreditCard.class).where(whereClause).run(CreditCard.class);
        connector.Close();
        return result;
    }

    @Override
    public Object SelectWhereAndGroupBy(String where, String groupBy) {
        Connector connector = MySqlConnectorFactory.createConnector();
        connector.Open();
        Object result = SelectQuery.create(connector, CreditCard.class).where(where).groupBy(groupBy).run(CreditCard.class);
        connector.Close();
        return result;
    }

    @Override
    public Object SelectGroupByAndHaving(String groupBy, String having) {
        Connector connector = MySqlConnectorFactory.createConnector();
        connector.Open();
        Object result = SelectQuery.create(connector, CreditCard.class).groupBy(groupBy).having(having).run(CreditCard.class);
        connector.Close();
        return result;
    }

    @Override
    public void Insert(Object newCreditCard) {
        Connector connector = MySqlConnectorFactory.createConnector();
        connector.Open();
        connector.Insert(CreditCard.class,(CreditCard) newCreditCard);
        connector.Close();
    }

    @Override
    public void Update(Object updatedCreditCard) {
        Connector connector = MySqlConnectorFactory.createConnector();
        connector.Open();
        connector.Update(CreditCard.class, (CreditCard) updatedCreditCard);
        connector.Close();
    }

    @Override
    public void Delete(Object deletedCreditCard) {
        Connector connector = MySqlConnectorFactory.createConnector();
        connector.Open();
        connector.Delete(CreditCard.class, (CreditCard) deletedCreditCard);
        connector.Close();
    }
}
