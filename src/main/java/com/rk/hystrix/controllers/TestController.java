package com.rk.hystrix.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rk.hystrix.dtos.BasePspsResponseDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class TestController {

	@GetMapping("/payment")
	@HystrixCommand(fallbackMethod = "paymentFallbackPsp", commandKey = "callFallbackPspsCmd")
	public ResponseEntity<BasePspsResponseDTO> payment() {

		LocalDateTime startTime = LocalDateTime.now();
		log.info("TestController|payment time : {}", LocalDateTime.now());

		final String ingenicoUri = "http://localhost:8080/ingenico/payment";

		log.info("Comminucating with  {}", ingenicoUri);
		RestTemplate restTemplate = new RestTemplate();
		String resp = restTemplate.getForObject(ingenicoUri, String.class);
		LocalDateTime endTime = LocalDateTime.now();
		Duration responseTime = Duration.between(startTime, endTime);

		return ResponseEntity.ok(BasePspsResponseDTO.builder().status("SUCCESS")
				.responseTime(String.valueOf(responseTime.getSeconds())).responseData(resp).build());

	}

	@HystrixCommand(fallbackMethod = "defaultPsp", commandKey = "callFallbackPspsCmd")
	public ResponseEntity<BasePspsResponseDTO> paymentFallbackPsp() {

		LocalDateTime startTime = LocalDateTime.now();
		log.info("TestController|payment time : {}", LocalDateTime.now());

		final String ingenicoUri = "http://localhost:8081/paypal/payment";

		log.info("Comminucating with  {}", ingenicoUri);
		RestTemplate restTemplate = new RestTemplate();
		String resp = restTemplate.getForObject(ingenicoUri, String.class);
		LocalDateTime endTime = LocalDateTime.now();
		Duration responseTime = Duration.between(startTime, endTime);

		return ResponseEntity.ok(BasePspsResponseDTO.builder().status("SUCCESS")
				.responseTime(String.valueOf(responseTime.getNano())).responseData(resp).build());

	}

	@GetMapping("/payment/noHystrix")
	public ResponseEntity<BasePspsResponseDTO> paymentNoHystrix() {

		LocalDateTime startTime = LocalDateTime.now();
		log.info("TestController|paymentNoHystrix time : {}", LocalDateTime.now());

		final String ingenicoUri = "http://localhost:8080/ingenico/payment";

		log.info("Comminucating with  {}", ingenicoUri);
		RestTemplate restTemplate = new RestTemplate();
		String resp = restTemplate.getForObject(ingenicoUri, String.class);
		LocalDateTime endTime = LocalDateTime.now();
		Duration responseTime = Duration.between(startTime, endTime);

		return ResponseEntity.ok(BasePspsResponseDTO.builder().status("SUCCESS")
				.responseTime(String.valueOf(responseTime.getNano())).responseData(resp).build());

	}

	public ResponseEntity<BasePspsResponseDTO> defaultPsp() {
		log.info("TestController|defaultPsp time : {}", LocalDateTime.now());
		return ResponseEntity.ok(BasePspsResponseDTO.builder().status("SUCCESS").message("DEFAULT PSP")
				.responseData("callFallbackPsps2").build());
	}
}
