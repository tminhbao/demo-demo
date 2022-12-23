package com.Repository;

import com.Connector.Connector;
import com.Connector.ConnectorFactory.MySqlConnectorFactory;
import com.Example.Account;
import com.Example.User;
import com.QueryBuilder.Implement.SelectQuery;

public class AccountRepository implements Repository {
    @Override
    public Object SelectAll() {
        Connector connector = MySqlConnectorFactory.createConnector();
        connector.Open();
        Object result =  SelectQuery.create(connector, Account.class).allRows().run(Account.class);
        connector.Close();
        return result;
    }

    @Override
    public Object SelectWhere(String whereClause) {
        Connector connector = MySqlConnectorFactory.createConnector();
        connector.Open();
        Object result = SelectQuery.create(connector, Account.class).where(whereClause).run(Account.class);
        connector.Close();
        return result;
    }

    @Override
    public Object SelectWhereAndGroupBy(String where, String groupBy) {
        Connector connector = MySqlConnectorFactory.createConnector();
        connector.Open();
        Object result = SelectQuery.create(connector, Account.class).where(where).groupBy(groupBy).run(Account.class);
        connector.Close();
        return result;
    }

    @Override
    public Object SelectGroupByAndHaving(String groupBy, String having) {
        Connector connector = MySqlConnectorFactory.createConnector();
        connector.Open();
        Object result = SelectQuery.create(connector, Account.class).groupBy(groupBy).having(having).run(Account.class);
        connector.Close();
        return result;
    }

    @Override
    public void Insert(Object newAccount) {
        Connector connector = MySqlConnectorFactory.createConnector();
        connector.Open();
        connector.Insert(Account.class,(Account) newAccount);
        connector.Close();
    }

    @Override
    public void Update(Object updatedAccount) {
        Connector connector = MySqlConnectorFactory.createConnector();
        connector.Open();
        connector.Update(Account.class, (Account) updatedAccount);
        connector.Close();
    }

    @Override
    public void Delete(Object deletedAccount) {
        Connector connector = MySqlConnectorFactory.createConnector();
        connector.Open();
        connector.Delete(Account.class, (Account)deletedAccount);
        connector.Close();
    }
}
