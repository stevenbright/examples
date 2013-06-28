package com.alexshabanov.jdbcprobe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Entry point.
 */
public final class App {
    public static void main(String[] args) throws Exception {
        String url = null;
        String user = null;
        String password = null;
        String jdbcClass = null;
        String sql = null;

        System.out.println("Usage: java -jar jdbc-probe.jar -user user -password test -url jdbc:h2:mem:test -class org.h2.Driver -sql 'SELECT 1'");
        for (int i = 0; i < args.length; ++i) {
            if ("-user".equals(args[i])) {
                user = args[++i];
            } else if ("-password".equals(args[i])) {
                password = args[++i];
            } else if ("-url".equals(args[i])) {
                url = args[++i];
            } else if ("-class".equals(args[i])) {
                jdbcClass = args[++i];
            } else if ("-sql".equals(args[i])) {
                sql = args[++i];
            } else {
                System.out.println("ERROR: Unknown key: " + args[i]);
                return;
            }
        }

        if (url == null || user == null || jdbcClass == null || password == null || sql == null) {
            System.out.println("ERROR: All the parameters are mandatory");
            return;
        }

        Class.forName(jdbcClass);

        final Connection connection = DriverManager.getConnection(url, user, password);
        try {
            execSql(connection, sql);
        } finally {
            connection.close();
        }
    }

    private static void execSql(Connection connection, String sql) throws SQLException {
        final Statement s = connection.createStatement();
        try {
            printResultSet(s.executeQuery(sql));
        } finally {
            s.close();
        }
    }

    private static void printResultSet(ResultSet rs) throws SQLException {
        System.out.println("===================");

        int count = 0;
        final int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            for (int i = 0; i < columnCount; ++i) {
                if (i > 0) {
                    System.out.print("  ::  ");
                }
                System.out.print(rs.getObject(i + 1));
            }
            System.out.println();

            ++count;
        }

        System.out.println("OK: " + count + " row(s) fetched.");
    }
}
