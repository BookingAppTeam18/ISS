package rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rest.student1.controller.PriceControllerTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PriceControllerTest.class})
public class RestExampleApplicationTests {

	@Test
	public void contextLoads() {
	}

}
