package com.example.gestion.tareas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.gestion.tareas")
@EntityScan(basePackages = "com.example.gestion.tareas")
@EnableJpaRepositories(basePackages = { "com.example.gestion.tareas.B_core", "com.example.gestion.tareas.C_persistence" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
