package com.upiita.msvc_cv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcCvApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcCvApplication.class, args);
	}

}
