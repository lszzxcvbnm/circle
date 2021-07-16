package com.circlett.demo.utils;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDUtil {
	@Bean
	public static String getUUID(){

		return UUID.randomUUID().toString().replaceAll("-","");
		
	}
	
}
