package com.example.backend_sistema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BackendSistemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendSistemaApplication.class, args);
		System.out.println("🍽️ ========================================");
		System.out.println("🍽️ API Restaurante Oaxaqueño INICIADA");
		System.out.println("🍽️ Puerto: http://localhost:8080");
		System.out.println("🍽️ Test: http://localhost:8080/api/users/test");
		System.out.println("🍽️ Login: POST http://localhost:8080/api/users/login");
		System.out.println("🍽️ ========================================");
	}
}