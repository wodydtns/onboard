package com.superboard.onbrd;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.superboard.onbrd.global.util.S3UploadService;

@SpringBootTest
class OnbrdApplicationTests {

	@Test
	void contextLoads() throws Exception {
		S3UploadService test = new S3UploadService();
		try {
			test.getObjectList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
