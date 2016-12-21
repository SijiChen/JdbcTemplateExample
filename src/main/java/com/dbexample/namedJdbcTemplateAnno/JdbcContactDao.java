package com.dbexample.namedJdbcTemplateAnno;

import com.dbexample.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sjchen on 12/20/16.
 */
@Component(value = "byInjectNamedParameterJdbcTemplate")
public class JdbcContactDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Contact findById(Long id) {
        String sql = "select * from contact where id=:contactId";
        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("contactId", id);
        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, (rs, rowNum) -> {
            Contact contact = new Contact();
            contact.setId(rs.getLong("id"));
            contact.setFirstName(rs.getString("first_name"));
            contact.setLastName(rs.getString("last_name"));
            contact.setBirthDate(rs.getDate("birth_date"));
            return contact;
        });

    }
    @PostConstruct
    public void init() {
        Assert.notNull(namedParameterJdbcTemplate);
    }
}
