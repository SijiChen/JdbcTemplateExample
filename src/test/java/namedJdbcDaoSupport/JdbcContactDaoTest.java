package namedJdbcDaoSupport;

import com.dbexample.domain.Contact;
import com.dbexample.namedJdbcDaoSupport.JdbcContactDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.sql.Date;
import java.util.List;

/**
 * Created by sjchen on 10/6/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring/app-context-xml.xml"})
public class JdbcContactDaoTest {

    @Autowired
    @Qualifier("byNamedParameterJdbcDaoSupport")
    JdbcContactDao jdbcContactDao;


    @Test
    public void testFindFirstNameById() throws Exception {
        String firstName = jdbcContactDao.findFirstNameById(1L);
        System.out.println(firstName);
        Assert.notNull(firstName);
    }

    @Test
    public void testFindById() throws Exception {
        Contact contact = jdbcContactDao.findById(1L);
        System.out.println(contact);
        Assert.notNull(contact);
    }

    @Test
    public void testCreateNew() {
        Contact contact = new Contact();
        contact.setBirthDate(new Date(123456789L));
        contact.setFirstName("Sc");
        contact.setLastName("Yong");
        jdbcContactDao.createNew(contact);
        System.out.println(jdbcContactDao.findAll());
    }

    @Test
    public void testFindAll() {
        List<Contact> contacts = jdbcContactDao.findAll();
        System.out.println(contacts.toString());
    }

    @Test
    public void testFindAllwithDetails() {
        List<Contact> contacts = jdbcContactDao.findAllWithDetails();
        System.out.println(contacts);
    }

}
