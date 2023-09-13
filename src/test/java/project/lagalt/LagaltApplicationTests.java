package project.lagalt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import project.lagalt.service.UserService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = LagaltApplication.class)
@ContextConfiguration
class LagaltApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		assertNotNull(userService);
	}

}
