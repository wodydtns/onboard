package com.superboard.onbrd.global.config;

import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Component
public class FCMConfig {

	@PostConstruct
	public void initialize() throws IOException {

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(
						GoogleCredentials.fromStream(new ClassPathResource("serviceAccountKey.json").getInputStream()))
				.setProjectId("on-the-board-14183").build();
		FirebaseApp.initializeApp(options);

	}
}
