package com.lagoinha.connect.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.springframework.stereotype.Service;

@Service
public class Criptografia {

	public String criptografia(String valor) {
		
		try {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
		    byte[] hash = algorithm.digest(valor.getBytes(StandardCharsets.UTF_8));

		    StringBuilder texto = new StringBuilder();
		    for (byte b : hash) {
		      texto.append(String.format("%02X", 0xFF & b));
		    }
		    return texto.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
