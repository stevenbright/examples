package com.mysite.springjdbcsample.dao;

import com.mysite.springjdbcsample.model.Country;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

public class CountryDaoImpl extends JdbcDaoSupport implements CountryDao {

    public CountryDaoImpl() {
    }

    private boolean initialized = false;

    private void ensureInitialized() {
        if (initialized) {
            return;
        }

        getJdbcTemplate().execute("drop table COUNTRY if exists;");
        getJdbcTemplate().execute("create table COUNTRY\n" +
                "(\n" +
                "   id integer identity primary key,\n" +
                "   name char (255),\n" +
                "   code_name char (255)\n" +
                ");");

        initialized = true;
    }

    public void save(Country country) {
        ensureInitialized();
        getJdbcTemplate().update(
                "insert into COUNTRY (name, code_name) values\n" +
                        "(?, ?)",
                new Object[] {
                        country.getName(),
                        country.getCodeName()
                });
    }

    public List<Country> getAll() {
        ensureInitialized();
        return (List<Country>) getJdbcTemplate().query(
                "select * from COUNTRY",
                new CountryRowMapper());
    }
}
