package com.jx.tennis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

import com.mxixm.fastboot.weixin.annotation.EnableWxMvc;

@SpringBootApplication
@EnableWxMvc
@EnableAsync
public class SpringbootwebdemoApplication  extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(SpringbootwebdemoApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringbootwebdemoApplication.class, args);
	}
}

