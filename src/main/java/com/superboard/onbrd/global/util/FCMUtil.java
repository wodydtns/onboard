package com.superboard.onbrd.global.util;

import com.superboard.onbrd.global.entity.FCMMessage;
import org.springframework.stereotype.Component;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

@Component
public class FCMUtil {

	/**
	 * @author : Park jae wook
	 * @Date : 2023. 3. 4.
	 * @Method : sendAndroidMessage
	 * @Return : String
	 * @Task : Android push message 전달 
	 * @ETC : setTtl - message time to live
 * 			  setPriority - message priority
 * 			  setDirectBootOk - direct boot mode 시 device에 메시지 전달
	 * 
	 */
	public String sendAndroidMessage(FCMMessage fcmMessage) throws FirebaseMessagingException{
		Message message = Message.builder()
				.setAndroidConfig(AndroidConfig.builder()
				.setTtl(3600)
				.setPriority(AndroidConfig.Priority.NORMAL)
				.setRestrictedPackageName("com.superboard.onbrd")
				.setDirectBootOk(true)
				.setNotification(AndroidNotification.builder() 
				.setIcon("@drawable/bling")
				.setTitle(fcmMessage.getTitle())
				.setBody(fcmMessage.getMessage())
				.build()).build())
				.putData("eventType",fcmMessage.getEventType())
				.putData("boardgameId",fcmMessage.getBoardgameId())
				.putData("title",fcmMessage.getTitle())
				.putData("message",fcmMessage.getMessage())
				.setToken(fcmMessage.getRegistrationToken()).build();
		String response = FirebaseMessaging.getInstance().send(message);
		
		return response;
				
	}
	
}
