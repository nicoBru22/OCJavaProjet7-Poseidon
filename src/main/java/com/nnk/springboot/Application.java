package com.nnk.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nnk.springboot.config.DotenvLoader;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
        DotenvLoader.loadEnv();
		SpringApplication.run(Application.class, args);
	}
}
