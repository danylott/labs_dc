package com.example.Servlet_OOP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private static final ConnectionPool cp = new ConnectionPool();

    private final String url = "jdbc:mysql://localhost:3306/test";
    private final String user = "root";
    private final String password = "password";
    private final int MAX_CONNECTIONS = 10;

    private final BlockingQueue<Connection> connections;

    public ConnectionPool() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found");
            e.printStackTrace();
        }

        connections = new LinkedBlockingQueue<Connection>(MAX_CONNECTIONS);

        try {
            for(int i = 0; i < MAX_CONNECTIONS; ++i) {
                connections.put(DriverManager.getConnection(url,user,password));
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static ConnectionPool getConnectionPool() {
        return cp;
    }

    public Connection getConnection() throws InterruptedException, SQLException {
        Connection c = connections.take();
        if (c.isClosed()) {
            c = DriverManager.getConnection(url,user,password);
        }
        return c;
    }

    public void releaseConnection(Connection c) throws InterruptedException {
        connections.put(c);
    }
}
