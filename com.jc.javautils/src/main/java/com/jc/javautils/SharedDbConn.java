package com.jc.javautils;

import java.sql.Connection;

public class SharedDbConn implements AutoCloseable {
    private final DbConnPool connPool;
    private final Connection conn;
    private boolean inUse;

    public SharedDbConn(final DbConnPool connPool, final Connection conn, final boolean inUse) {
        this.connPool = connPool;
        this.conn = conn;
        this.inUse = inUse;
    }

    public Connection getConn() {
        return conn;
    }

    public synchronized boolean isInUse() {
        return inUse;
    }

    public synchronized void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    @Override
    public void close() {
        connPool.releaseConn(this);
    }
}
