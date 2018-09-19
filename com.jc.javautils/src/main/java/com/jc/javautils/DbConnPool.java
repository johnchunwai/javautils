package com.jc.javautils;

public interface DbConnPool {
    SharedDbConn acquireConn() throws InterruptedException;
    void releaseConn(final SharedDbConn conn);
}
