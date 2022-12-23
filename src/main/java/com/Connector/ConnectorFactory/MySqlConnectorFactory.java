package com.Connector.ConnectorFactory;

import com.Connector.*;

public class MySqlConnectorFactory extends ConnectorFactory {
    private MySqlConnectorFactory() { }

    public static Connector createConnector() {
        return new MySqlConnector(Url, Username, Password);
    }
}
