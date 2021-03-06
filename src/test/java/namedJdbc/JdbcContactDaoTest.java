package namedJdbc;

import com.dbexample.domain.Contact;
import com.dbexample.namedJdbcTemplateAnno.JdbcContactDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Created by sjchen on 10/6/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring/app-context-xml.xml"})
public class JdbcContactDaoTest {

    @Autowired
    @Qualifier("byInjectNamedParameterJdbcTemplate")
    JdbcContactDao jdbcContactDao;


    @Test
    public void testFindFirstNameById() throws Exception {
        Contact contact = jdbcContactDao.findById(1L);
        System.out.println(contact);
        Assert.notNull(contact);
    }



}
