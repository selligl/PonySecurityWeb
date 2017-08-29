package uk.co.poney.ponysecurity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class PonysecurityApplicationTests {

    @Value("${application.name}")
    private String applicationName;

    @Test
    public void contextLoads() {
        //Test if app loads
        assertNotNull(this.applicationName);
    }

}
