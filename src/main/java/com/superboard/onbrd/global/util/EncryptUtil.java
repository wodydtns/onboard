package com.superboard.onbrd.global.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import org.springframework.stereotype.Component;

import com.superboard.onbrd.global.entity.RSA;


@Component
public class EncryptUtil {

	public String getSHA256Encrypt(String text, String salt) throws Exception {
		String encryptedStr = "";
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(salt.getBytes("UTF-8"));
		byte[] bytes = md.digest(text.getBytes("UTF-8"));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		encryptedStr = sb.toString();
		return encryptedStr;
	}
	
	public RSA createRSAEncrypt() throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
		keyGenerator.initialize(2048);
		KeyPair keyPair = keyGenerator.genKeyPair();
		KeyFactory keyFactory =KeyFactory.getInstance("RSA");
		
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		
		RSAPublicKeySpec publicSpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
		String publicKeyModulus = publicSpec.getModulus().toString(16);
		String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
		RSA rsa = new RSA(privateKey,publicKeyModulus,publicKeyExponent);
		return rsa;
	}

}
