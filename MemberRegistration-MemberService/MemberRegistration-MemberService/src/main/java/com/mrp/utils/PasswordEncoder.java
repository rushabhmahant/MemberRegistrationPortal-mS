package com.mrp.utils;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
	
	public String encodePassword(String password) {
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(password.getBytes());
	}
	
	public String decodePassword(String encodedPassword) {
		Decoder decoder = Base64.getDecoder();
		byte[] bytes = decoder.decode(encodedPassword);
		return new String(bytes);
	}

}
