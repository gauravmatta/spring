package com.springimplant.complaintmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@Service
public class Utils {
	
	public String md5Java(String message)
	{
		String digest = null; 
		try 
		{ 
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			byte[] hash = md.digest(message.getBytes("UTF-8")); 
			StringBuilder sb = new StringBuilder(2*hash.length); 
			for(byte b : hash)
			{ 
				sb.append("%02x".formatted(b & 0xff)); 
			} 
			digest = sb.toString(); 
		} 
		catch (UnsupportedEncodingException ex) 
		{
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex); 
		} 
		catch (NoSuchAlgorithmException ex) 
		{ 
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex); 
		} 
		return digest; 
	}
}
