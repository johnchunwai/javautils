package com.jc.javautilsmain;

import com.jc.javautils.Barrier;
import com.jc.javautils.DbConnPool;

import java.sql.ResultSet;

public class QueryRunnable implements Runnable {
    private final DbConnPool connPool;
    private final Barrier barrier;
    private final int threadId;


    public QueryRunnable(final int threadId, final DbConnPool connPool, final Barrier barrier) {
        this.connPool = connPool;
        this.barrier = barrier;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        System.out.println("thread " + threadId + " entering barrier");
        try {
            barrier.enter();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // make 5 queries
        System.out.println("Begin executing for thread " + threadId);
        for (int i = 0; i < 5; i++) {
            try (final var sharedConn = connPool.acquireConn();
                 final var statement = sharedConn.getConn().prepareStatement(
                         "SELECT count(*) FROM employee")) {
                final ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    System.out.println("thread " + threadId + " returns " + resultSet.getString(1));
                }
            } catch (Exception e) {
                System.out.println(e);
                System.exit(1);
            }
            System.out.println("End executing for thread " + threadId);
        }
    }
}
