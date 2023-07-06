package com.superboard.onbrd.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.superboard.onbrd.global.entity.FCMMessageDto;
import com.superboard.onbrd.home.dto.PushMessageResponse;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class FCMUtil {

    private final ObjectMapper objectMapper;

    /**
     * @author : Park jae wook
     * @Date : 2023. 3. 4.
     * @Method : sendAndroidMessage
     * @Return : String
     * @Task : Android push message 전달
     * @ETC : setTtl - message time to live
     * setPriority - message priority
     * setDirectBootOk - direct boot mode 시 device에 메시지 전달
     */
    private String getAccessToken() throws IOException {
        // firebase로 부터 access token을 가져온다.

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("serviceAccountKey.json").getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();

        return googleCredentials.getAccessToken().getTokenValue();

    }

    public String makeMessage(PushMessageResponse pushMessageResponse) throws JsonProcessingException {
        FCMMessageDto fcmMessage = FCMMessageDto.builder()
                .message(FCMMessageDto.Message.builder()
                        .token(pushMessageResponse.getTargetToken())
                        .notification(
                                FCMMessageDto.Notification.builder().title(pushMessageResponse.getTitle()).body(pushMessageResponse.getBody()).build())
                        .data(FCMMessageDto.Data.builder().eventType(pushMessageResponse.getEventType()).boardgameId(pushMessageResponse.getBoardgameId())
                                .notificationId(pushMessageResponse.getNotificationId())
                                .build())
                        .build()
                )
                .validateOnly(false)
                .build();

        return objectMapper.writeValueAsString(fcmMessage);

    }

    public String sendMessageTo(PushMessageResponse pushMessageResponse) throws IOException {
        String message = makeMessage(pushMessageResponse);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/v1/projects/on-the-board-8adc5/messages:send")
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();
        System.out.println("request: " + request);
        Response response = client.newCall(request).execute();

        return response.toString();
    }


}
