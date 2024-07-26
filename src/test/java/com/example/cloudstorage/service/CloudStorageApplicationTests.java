package com.example.cloudstorage.service;

import com.example.cloudstorage.controller.JwtAuthenticationFilter;
import com.example.cloudstorage.controller.entities.ListItem;
import com.example.cloudstorage.controller.entities.LoginBody;
import com.example.cloudstorage.controller.entities.LoginResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Objects;

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
		var firstAppPort = app.getMappedPort(8080);
		var loginBody = new LoginBody("lalita", "123456");
		var authToken = Objects.requireNonNull(
				restTemplate
						.postForEntity("http://localhost:" + firstAppPort + "/login", loginBody, LoginResponse.class)
						.getBody()
		).getAuthToken();

		var headers = new HttpHeaders();
		headers.add(JwtAuthenticationFilter.HEADER_NAME, JwtAuthenticationFilter.BEARER_PREFIX + authToken);
		var response = restTemplate.exchange(
				"http://localhost:" + firstAppPort + "/list",
				HttpMethod.GET,
				new HttpEntity<>(headers),
				List.class
		).getBody();
        assert response != null;
        Assertions.assertTrue(response.isEmpty());
	}

	@AfterAll
	public static void after() {
		postgres.stop();
	}

}
