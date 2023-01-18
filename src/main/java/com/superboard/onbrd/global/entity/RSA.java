package com.superboard.onbrd.global.entity;

import java.security.PrivateKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RSA {
	private PrivateKey privateKey;
	private String modulus;
	private String exponent;

}
