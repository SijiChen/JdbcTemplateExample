package com.dbexample.jdbcdaosupport;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by sjchen on 10/6/16.
 */
public class JdbcContactDao extends JdbcDaoSupport {


    public String findFirstNameById(Long id) {
        return getJdbcTemplate().queryForObject(
                "select first_name from contact where id = ?",
                new Object[]{id}, String.class);
    }


}
