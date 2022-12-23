package com.Connector;

public class MsSqlConnector extends Connector {
    public MsSqlConnector(String url, String username, String password, String port, String databaseName) {
        super.url = url + ":" + port + ";" + "databaseName=" + databaseName;
        super.username = username;
        super.password = password;
    }
}
