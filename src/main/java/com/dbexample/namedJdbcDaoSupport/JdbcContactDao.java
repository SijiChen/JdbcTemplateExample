package com.dbexample.namedJdbcDaoSupport;

import com.dbexample.domain.Contact;
import com.dbexample.domain.ContactTelDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sjchen on 10/9/16.
 */
@Service(value = "byNamedParameterJdbcDaoSupport")
@Transactional
public class JdbcContactDao extends NamedParameterJdbcDaoSupport {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public String findFirstNameById(Long id) {
        String sql = "select first_name from contact where id = :contactId";


        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("contactId", id);

        return getNamedParameterJdbcTemplate().queryForObject(sql, namedParameters, String.class);
    }

    public Contact findById(Long id) {
        String sql = "select * from contact where id=:contactId";
        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("contactId", id);
        return getNamedParameterJdbcTemplate().queryForObject(sql, namedParameters, (rs, rowNum) -> {
            Contact contact = new Contact();
            contact.setId(rs.getLong("id"));
            contact.setFirstName(rs.getString("first_name"));
            contact.setLastName(rs.getString("last_name"));
            contact.setBirthDate(rs.getDate("birth_date"));
            return contact;
        });

    }

    public Contact createNew(Contact contact) {
        String sql = "insert into Contact (first_name, last_name, birth_date) values(:firstName,:lastName,:birthDate)";
        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("firstName", contact.getFirstName());
        namedParameters.put("lastName", contact.getLastName());
        namedParameters.put("birthDate", contact.getBirthDate());
        getNamedParameterJdbcTemplate().update(sql, namedParameters);
        return contact;

    }

    public List<Contact> findAll() {
        String sql = "select id, first_name, last_name, birth_date from contact";
        return getNamedParameterJdbcTemplate().query(sql, (rs, rowNum) -> {
            Contact contact = new Contact();
            contact.setId(rs.getLong("id"));
            contact.setFirstName(rs.getString("first_name"));
            contact.setLastName(rs.getString("last_name"));
            contact.setBirthDate(rs.getDate("birth_date"));
            return contact;
        });
    }

    /**
     * ResultSetExtractor:in which we need to retrieve the data from the parent (CONTACT) and
     * child (CONTACT_TEL_DETAIL) tables with a join and then transform the data back into
     * the nested object (ContactTelDetail within Contact) accordingly
     *
     * @return
     */
    public List<Contact> findAllWithDetails() {
        String sql = "select c.id, c.first_name, c.last_name, c.birth_date" +
                ", t.id as contact_tel_id, t.tel_type, t.tel_number from contact c " +
                "left join contact_tel_detail t on c.id = t.contact_id";
        return getNamedParameterJdbcTemplate().query(sql, (ResultSet rs) -> {
            Map<Long, Contact> map = new HashMap<Long, Contact>();
            Contact contact = null;
            while (rs.next()) {
                Long id = rs.getLong("id");
                contact = map.get(id);
                if (contact == null) {
                    contact = new Contact();
                    contact.setId(id);
                    contact.setFirstName(rs.getString("first_name"));
                    contact.setLastName(rs.getString("last_name"));
                    contact.setBirthDate(rs.getDate("birth_date"));
                    contact.setContactTelDetails(new ArrayList<ContactTelDetail>());
                    map.put(id, contact);
                }
                Long contactTelDetailId = rs.getLong("contact_tel_id");
                if (contactTelDetailId > 0) {
                    ContactTelDetail contactTelDetail = new ContactTelDetail();
                    contactTelDetail.setId(contactTelDetailId);
                    contactTelDetail.setContactId(id);
                    contactTelDetail.setTelType(rs.getString("tel_type"));
                    contactTelDetail.setTelNumber(rs.getString("tel_number"));
                    contact.getContactTelDetails().add(contactTelDetail);
                }
            }
            return new ArrayList<>(map.values());
        });


    }


}
