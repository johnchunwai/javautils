package com.jc.javautils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnPool implements DbConnPool {
    private final SharedDbConn[] connList;


    public MySqlConnPool(final int size) throws ClassNotFoundException, SQLException {
        // initialize connections in the pool
        connList = new SharedDbConn[size];
        Class.forName("com.mysql.jdbc.Driver");
        for (int i = 0; i < size; i++) {
            final Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/javatest?useSSL=false&user=root&password=root123");
            connList[i] = new SharedDbConn(this, conn, false);
        }
    }

    @Override
    public synchronized SharedDbConn acquireConn() throws InterruptedException {
        while (true) {
            for (int i = 0; i < connList.length; i++) {
                final SharedDbConn conn = connList[i];
                if (!conn.isInUse()) {
                    // found connection! Claim it.
                    conn.setInUse(true);
                    return conn;
                }
            }
            // not found
            wait();
        }
    }

    @Override
    public synchronized void releaseConn(final SharedDbConn conn) {
        conn.setInUse(false);
        notify();
    }
}
