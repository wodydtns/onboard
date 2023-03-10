package com.superboard.onbrd;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.superboard.onbrd.global.util.OciObjectStorageUtil;

@SpringBootTest
class OnbrdApplicationTests {

	@Test
	void contextLoads() throws Exception {
		OciObjectStorageUtil oci = new OciObjectStorageUtil();
		
		boolean isExist = oci.fileExists("sign-in.html", "review/");
		assertTrue(isExist);
	}

}
