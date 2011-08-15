package com.truward.hdbdemo.dal.services;

import com.truward.hdbdemo.dal.beans.Profile;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;


public class SimpleHsqlAccessor {
    private final static String PERSON_DB_SCHEMA =
            "DROP TABLE PERSON IF EXISTS;\n" +
                    "\n" +
                    "CREATE TABLE PERSON (\n" +
                    "   ID integer identity primary key,  \n" +
                    "   USERNAME varchar(64) not null,\n" +
                    "   PASSWORD_HASH varchar(64) not null,\n" +
                    "   EMAIL varchar(64) not null,\n" +
                    "   SUBSCRIBED_TO_NEWS bit not null,\n" +
                    "   AGE integer not null,\n" +
                    "   CONSTRAINT IDX_PERSON_ID PRIMARY KEY (ID)\n" +
                    ");\n" +
                    "\n" +
                    "INSERT INTO PERSON(ID, USERNAME, PASSWORD_HASH, EMAIL, SUBSCRIBED_TO_NEWS, AGE)\n" +
                    "   VALUES(1, 'don', '123', 'cavedave@dot.cn', 1, 15);\n" +
                    "INSERT INTO PERSON(ID, USERNAME, PASSWORD_HASH, EMAIL, SUBSCRIBED_TO_NEWS, AGE)\n" +
                    "   VALUES(2, 'fred', 'qwe', 'sir.fred@castorama.com', 0, 23);\n" +
                    "INSERT INTO PERSON(ID, USERNAME, PASSWORD_HASH, EMAIL, SUBSCRIBED_TO_NEWS, AGE)\n" +
                    "   VALUES(3, 'eva', 'a', 'just.a@code.aol.us', 1, 19);\n";

    private final static String FIND_PERSON_BY_ID =
            "SELECT ID, USERNAME, PASSWORD_HASH, EMAIL, SUBSCRIBED_TO_NEWS, AGE FROM PERSON WHERE ID=?";

    

    // playing in a traditional way
    public static void playWithHsqldb1() throws Exception {
        Class.forName("org.hsqldb.jdbcDriver");

        final Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem", "SA", "");

        try {
            final Statement statement = connection.createStatement();
            statement.executeQuery(PERSON_DB_SCHEMA);

            System.out.println("HSQLDB: data added");

            //...
        } finally {
            connection.close();
        }
    }

    // playing in a spring way
    public static void playWithHsqldb2(ApplicationContext context) throws Exception {
        final DataSource dataSource = (DataSource) context.getBean("testDataSource");
        final Statement schemaStatement = dataSource.getConnection().createStatement();
        schemaStatement.executeQuery(PERSON_DB_SCHEMA);

        final SimpleJdbcTemplate simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
        final ParameterizedRowMapper<Profile> rowMapper = new ParameterizedRowMapper<Profile>() {
            public Profile mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                final Profile profile = new Profile();
                profile.setId(resultSet.getInt("ID"));
                profile.setUsername(resultSet.getString("USERNAME"));
                profile.setPasswordHash(resultSet.getString("PASSWORD_HASH"));
                profile.setEmail(resultSet.getString("EMAIL"));
                profile.setSubscribedToNews(resultSet.getInt("SUBSCRIBED_TO_NEWS") != 0);
                profile.setAge(resultSet.getInt("AGE"));
                return profile;
            }
        };

        Profile profile;

        profile = simpleJdbcTemplate.queryForObject(FIND_PERSON_BY_ID, rowMapper, 1);
        System.out.println("#1: " + profile.toString());

        profile = simpleJdbcTemplate.queryForObject(FIND_PERSON_BY_ID, rowMapper, 2);
        System.out.println("#2: " + profile.toString());
    }
}
