package com.superboard.onbrd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import com.superboard.onbrd.global.util.OciObjectStorageUtil;

@SpringBootTest
class OnbrdApplicationTests {

	@Test
	void contextLoads() throws Exception {
		OciObjectStorageUtil test = new OciObjectStorageUtil();
		try {
			FileInputStream fileInputStream = new FileInputStream(new File("E:/220709.png"));
			String fileName = "220709";
			String contentType = "image/png";
			MockMultipartFile file = new MockMultipartFile(fileName, fileInputStream);
			boolean successFlag = test.UploadObject(file);
			assertTrue(successFlag);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
