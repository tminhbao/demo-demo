package com.Connector.ConnectorFactory;

public abstract class ConnectorFactory {
    protected static String Url = "";
    protected static String Username = "";
    protected static String Password = "";
    protected static String Port = "";
    protected static String DatabaseName = "";

    static public void provideConnectionInfo(String newUrl, String newUsername, String newPassword, String newPort, String newDatabaseName) {
        Url = newUrl;
        Username = newUsername;
        Password = newPassword;
        Port = newPort;
        DatabaseName = newDatabaseName;
    }
    static public void provideConnectionInfo(String newUrl, String newUsername, String newPassword) {
        Url = newUrl;
        Username = newUsername;
        Password = newPassword;
        Port = "";
        DatabaseName = "";
    }
}
