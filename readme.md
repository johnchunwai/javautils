* Build

mvn clean install

or

javac -d mod --module-source-path ./*/src/main/java/ ./com.jc.javautils/src/main/java/module-info.java ./com.jc.javautils/src/main/java/com/jc/javautils/Barrier.java ./com.jc.javautils/src/main/java/com/jc/java
utils/DbConnPool.java ./com.jc.javautils/src/main/java/com/jc/javautils/MySqlConnPool.java ./com.jc.javautils/src/main/java/com/jc/javautils/SharedDbConn.java ./com.jc.javautilsmain/src/main/java/module-info.java ./com.jc.javautilsm
ain/src/main/java/com/jc/javautilsmain/PoolMain.java ./com.jc.javautilsmain/src/main/java/com/jc/javautilsmain/QueryRunnable.java

* Run

mvn exec:exec -DoptionalArg1="100"

or

java -p mod;C:\Users\wai\.m2\repository\mysql\mysql-connector-java\5.1.40\ -m com.jc.javautilsmain/com.jc.javautilsmain.PoolMain 100