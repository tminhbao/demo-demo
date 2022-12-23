package com.Connector.ConnectorFactory;

import com.Connector.Connector;
import com.Connector.*;

public class MsSqlConnectorFactory extends ConnectorFactory {
    private MsSqlConnectorFactory() { }

    public static Connector createConnector() {
        return new MsSqlConnector(Url, Username, Password, Port, DatabaseName);
    }
}
