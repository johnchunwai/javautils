package com.jc.javautilsmain;

import com.jc.javautils.Barrier;
import com.jc.javautils.DbConnPool;
import com.jc.javautils.MySqlConnPool;

import java.sql.SQLException;

public class PoolMain {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
        if (args.length != 1) {
            System.out.println("Missing argument for numThreads.");
            System.exit(-1);
        }

        final int numThreads = Integer.parseInt(args[0]);

        final DbConnPool connPool = new MySqlConnPool(10);

        // Use a barrier to force all threads to start at the same time
        final Barrier barrier = new Barrier(numThreads);
        for (int i = 0; i < 100; i++) {
            new Thread(new QueryRunnable(i, connPool, barrier)).start();
        }
        barrier.release();
    }


}
