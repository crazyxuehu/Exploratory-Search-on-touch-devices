package UnitTest;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:spring-mvc.xml",
		"classpath*:applicationcontext.xml",
        "classpath*:web.xml"})
public class BaseTest {
	@Resource
    WebApplicationContext wac;
	MockMvc mockMvc;
	@Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }
}
