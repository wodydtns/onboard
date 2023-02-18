package com.superboard.onbrd;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.superboard.onbrd.global.util.OciObjectStorageUtil;

@SpringBootTest
class OnbrdApplicationTests {

	@Test
	void contextLoads() throws Exception {
		OciObjectStorageUtil test = new OciObjectStorageUtil();
		try {
			test.getObjectList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
