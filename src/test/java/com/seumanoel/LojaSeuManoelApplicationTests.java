package com.seumanoel;

import com.seumanoel.config.BaseTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class LojaSeuManoelApplicationTests extends BaseTestConfig {

	@Test
	void contextLoads() {
	}

}
