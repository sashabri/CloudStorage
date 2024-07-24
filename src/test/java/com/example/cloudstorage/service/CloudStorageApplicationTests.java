package com.example.cloudstorage.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Container
	private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
			"postgres:14-alpine"
	);

	@Container
	private static final GenericContainer<?> app = new GenericContainer<>("client:0.0.1")
			.withExposedPorts(8080);

	@BeforeAll
	public static void before() {
		postgres
				.withUsername("postgres")
				.withPassword("postgres")
				.withDatabaseName("postgres")
				.start();
	}

	@Test
	void contextLoads() {
		app.start();
	}

	@AfterAll
	public static void after() {
		postgres.stop();
	}

}
