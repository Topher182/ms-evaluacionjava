package com.msevaluacionjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

@SpringBootApplication
public class MsEvaluacionjavaApplication {
	@Bean
	public HttpHeaders httpHeaders() {
		return new HttpHeaders();
	}
	public static void main(String[] args) {
		SpringApplication.run(MsEvaluacionjavaApplication.class, args);
	}
}