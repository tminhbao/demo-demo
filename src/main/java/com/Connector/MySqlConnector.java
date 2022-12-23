package com.Connector;

public class MySqlConnector extends Connector {
    public MySqlConnector(String url, String username, String password) {
        super.url = url;
        super.username = username;
        super.password = password;
    }
}
