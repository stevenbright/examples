jdbc-probe
==========

## Build

mvn clean install

## Usage


H2:

java -cp /home/alex/.m2/repository/com/h2database/h2/1.3.170/h2-1.3.170.jar:./target/jdbc-probe-1.0-SNAPSHOT.jar com.alexshabanov.jdbcprobe.App -user SA -password '' -url jdbc:h2:mem:test -class org.h2.Driver -sql 'SELECT 1'
1
OK: 1 row(s) fetched.

Oracle:

java -cp /home/alex/.m2/repository/com/oracle/ojdbc6/11.2/ojdbc6-11.2.jar:./target/jdbc-probe-1.0-SNAPSHOT.jar com.alexshabanov.jdbcprobe.App -user MyUser -password MyPassword -url jdbc:oracle:thin:@host:1521:XE -class oracle.jdbc.OracleDriver -sql 'SELECT 1 FROM dual'
1
OK: 1 row(s) fetched.


