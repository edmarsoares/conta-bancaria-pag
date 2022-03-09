package br.com.contabancaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ApiContaBancariaPagSeguroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiContaBancariaPagSeguroApplication.class, args);
	}

}
