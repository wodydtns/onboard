package com.superboard.onbrd;

import com.superboard.onbrd.global.util.FCMUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootTest
class OnbrdApplicationTests {

    @Autowired
    private FCMUtil fcmUtil;
    @Test
    void contextLoads() throws Exception {
        String registrationToken = "fph6EGd9QcitzfL0kp8n2N:APA91bG_ZwzMerjlolwjSCZGcw59JcsZj-9eOS0kcsCbgP4RHt5DrsBd6ZniKcl6jsQXYhNIZkOFwkVagnuSxuvSW0tZNH1o60nX2xNPU1TvWN6rCUtuEtLmIfob9xlkgxoaK3hRKF_9";
        String title = "Test Notification";
        String body = "This is a test notification.";

//        String response = fcmUtil.sendAndroidMessage(registrationToken,title,body);
//        System.out.println(response.toString());
    }

}
