package com.example.nomad.nomad;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NomadApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;
	@Test
	void contextLoads() {
	}

	@Test
	void shouldReturnRoles(){
		ResponseEntity<String> response = restTemplate
				.getForEntity("/api/v1/roles", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
//
//	@Test
//	void shouldCreateARole(){
//
//			ResponseEntity<String> response = restTemplate
//					.getForEntity("/api/v1/roles", String.class);
//			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
////
////			DocumentContext documentContext = JsonPath.parse(response.getBody());
////			Number id = documentContext.read("$.id");
////			assertThat(id).isEqualTo(99);
////
////			Double amount = documentContext.read("$.amount");
////			assertThat(amount).isEqualTo(123.45);
//
//	}

}
