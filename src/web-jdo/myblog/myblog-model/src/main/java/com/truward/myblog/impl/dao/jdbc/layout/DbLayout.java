package com.truward.myblog.impl.dao.jdbc.layout;

import java.io.*;

/**
 * Determines db layout.
 */
public final class DbLayout {

    private static String schema;

    public static String getSchema() {
        if (schema == null) {
            final InputStream inputStream = DbLayout.class.getResourceAsStream("hsqldb_schema.sql");

            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final byte[] buffer = new byte[1024];

            try {
                for (;;) {
                    final int bytesRead = inputStream.read(buffer);
                    outputStream.write(buffer, 0, bytesRead);
                    if (bytesRead < buffer.length) {
                        break;
                    }
                }

                schema = new String(outputStream.toByteArray(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException("Can't convert source schema file's contents", e);
            } catch (IOException e) {
                throw new IllegalStateException("Can't read source schema file", e);
            }
        }

        return schema;
    }

    // table names

    public static final String PROFILE_TABLE_NAME = "PROFILE";

    public static final String ROLE_TABLE_NAME = "ROLE";

    public static final String PROFILE_TO_ROLE_TABLE_NAME = "PROFILE_TO_ROLE";

    // column names

    public static final String COL_ID = "ID";

    public static final String COL_PROFILE_LOGIN = "LOGIN";

    public static final String COL_PROFILE_EMAIL = "EMAIL";

    public static final String COL_PROFILE_PASSWORD = "PASSWORD";

    public static final String COL_PROFILE_CREATED = "CREATED";


    public static final String COL_ROLE_NAME = "NAME";


    public static final String COL_PROFILE_TO_ROLE_PROFILE_ID = "PROFILE_ID";

    public static final String COL_PROFILE_TO_ROLE_ROLE_ID = "ROLE_ID";



    // common name for ID parameter
    public static final String PAR_ID = "PAR_ID";


    //
    // parameter names
    //

    public static final String PAR_PROFILE_ID = "PAR_PROFILE_ID";

    public static final String PAR_PROFILE_LOGIN = "PAR_PROFILE_LOGIN";

    public static final String PAR_PROFILE_EMAIL = "PAR_PROFILE_EMAIL";

    public static final String PAR_PROFILE_PASSWORD = "PAR_PROFILE_PASSWORD";
    
    public static final String PAR_PROFILE_CREATED = "PAR_PROFILE_CREATED";

    
    public static final String PAR_ROLE_NAME = "PAR_ROLE_NAME";

    
    //
    // queries
    //
//
//    public static final String INSERT_PROFILE_QUERY = "insert into PROFILE(LOGIN, EMAIL, PASSWORD, CREATED)\n" +
//            "values (:PAR_PROFILE_LOGIN, :PAR_PROFILE_EMAIL, :PAR_PROFILE_PASSWORD, now())";

    public static final String GET_PROFILES_QUERY = "select\n" +
            "   ID as PAR_ID, LOGIN as PAR_PROFILE_LOGIN, EMAIL as PAR_PROFILE_EMAIL,\n" +
            "   PASSWORD as PAR_PROFILE_PASSWORD, CREATED as PAR_PROFILE_CREATED\n" +
            "   from PROFILE order by CREATED";

    public static final String GET_PROFILE_BY_LOGIN_QUERY = "select\n" +
            "   ID as PAR_ID, LOGIN as PAR_PROFILE_LOGIN, EMAIL as PAR_PROFILE_EMAIL,\n" +
            "   PASSWORD as PAR_PROFILE_PASSWORD, CREATED as PAR_PROFILE_CREATED\n" +
            "   from PROFILE where LOGIN = :PAR_PROFILE_LOGIN";


    public static final String UPDATE_PROFILE_QUERY = "update PROFILE set\n" +
            "   LOGIN = :PAR_PROFILE_LOGIN, EMAIL = :PAR_PROFILE_EMAIL,\n" +
            "   PASSWORD = :PAR_PROFILE_PASSWORD\n" +
            "where ID = :PAR_ID";

    public static final String REMOVE_PROFILE_QUERY = "delete from PROFILE where ID = :PAR_ID";



    public static final String GET_ROLE_BY_NAME_QUERY = "select\n" +
            "   ID as PAR_ID, NAME as PAR_ROLE_NAME\n" +
            "from ROLE where NAME = :PAR_ROLE_NAME";

    public static final String GET_ROLES_FOR_PROFILE_QUERY = "select\n" +
            "   R.ID as PAR_ID, R.NAME as PAR_ROLE_NAME\n" +
            "from PROFILE_TO_ROLE as PR\n" +
            "inner join ROLE as R on R.ID = PR.ROLE_ID and PR.PROFILE_ID = :PAR_PROFILE_ID";

    
    public static final String REMOVE_PROFILE_ROLES_QUERY = "delete from PROFILE_TO_ROLE\n" +
            "where PROFILE_ID = :PAR_PROFILE_ID";

    /**
     * Hidden constructor.
     */
    private DbLayout() {}
}
