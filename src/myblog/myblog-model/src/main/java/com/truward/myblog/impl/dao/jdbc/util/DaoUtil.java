package com.truward.myblog.impl.dao.jdbc.util;

import java.util.Date;


public final class DaoUtil {
    public static String adapt(String val) {
        if (val != null) {
            return val;
        }
        return ""; // return the empty string instead of null value
    }

    public static Date adapt(Date date) {
        if (date != null) {
            return date;
        }
        return new Date();
    }

//    public static <T, K> T findObjectByItem(SimpleJdbcTemplate template, ParameterizedRowMapper<T> rowMapper, String sql,
//                                            String keyName, K keyValue) {
//        final MapSqlParameterSource parameters = new MapSqlParameterSource(keyName, keyValue);
//        final List<T> objects = template.query(sql, rowMapper, parameters);
//
//    }

    private DaoUtil() {}
}
