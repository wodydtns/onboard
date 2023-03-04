package com.superboard.onbrd;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.superboard.onbrd.global.util.FCMUtil;

@SpringBootTest
class OnbrdApplicationTests {

	@Test
	void contextLoads() throws Exception {
		FCMUtil fcmUtil = new FCMUtil();
		String targetToken = "BMf038cb7t9f7xmSJaZpVswxh3ZHvwYR3OfgsSQwqSQp0tlrnB6efqff5aog_nPTRyLIiijXge5AwhQeyR-z5-4";
		String title = "타이틀";
		String body ="바디";
		String res = fcmUtil.sendAndroidMessage(0, targetToken, title, body);
		assertFalse(res.isEmpty());
	}

}
