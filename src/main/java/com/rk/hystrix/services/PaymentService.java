package com.rk.hystrix.services;

import java.time.LocalDateTime;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rk.hystrix.dtos.BasePspsResponseDTO;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class PaymentService {
	
	@HystrixCommand(fallbackMethod = "callFallbackPsp", commandKey = "callFallbackPspsCmd")
	public String payment(BasePspsResponseDTO dto) {
		log.info("PaymentService|payment|Entry time : {}", LocalDateTime.now());
		final String ingenicoUri = "http://localhost:8080/ingenico/payment";
		log.info("Comminucating with pspName :  {}", ingenicoUri);
		RestTemplate restTemplate = new RestTemplate();
		String resp = restTemplate.getForObject(ingenicoUri, String.class);
		
		log.info("PaymentService|payment|Exit time : {}", LocalDateTime.now());
		return resp;

	}
	// pass Object in the input
	@HystrixCommand(fallbackMethod = "callDefaultPsp", commandKey = "callFallbackPspsCmd")
	public String callFallbackPsp(BasePspsResponseDTO dto,Throwable t) { //Throwable t
		log.info("PaymentService|callFallbackPsp|Entry time : {}", LocalDateTime.now());
		log.warn("Fallback Reason: ",t.getMessage());
		String fallBackPsps="paypal";
		
		final String ingenicoUri = dto.getFallbackUrl();
		
		dto.setFallbackUrl("/googlePay");
		
		log.info("Comminucating with fallBackPsps {} {}",ingenicoUri, ingenicoUri);
		RestTemplate restTemplate = new RestTemplate();
		String resp = restTemplate.getForObject(ingenicoUri, String.class);
		
		log.info("PaymentService|callFallbackPsp|Exit time : {}", LocalDateTime.now());
		return resp;

	}
	
	public String callDefaultPsp(BasePspsResponseDTO dto) {
		
		return "callDefaultPsp: "+dto.getFallbackUrl();

	}
	

	public String paymentWithoutHystrix() {
		log.info("PaymentService|paymentWithoutHystrix|Entry time : {}", LocalDateTime.now());
		final String ingenicoUri = "http://localhost:8080/ingenico/payment";
		
		log.info("Comminucating with  {}", ingenicoUri);
		RestTemplate restTemplate = new RestTemplate();
		String resp = restTemplate.getForObject(ingenicoUri, String.class);
		
		log.info("PaymentService|paymentWithoutHystrix|Exit time : {}", LocalDateTime.now());
		return resp;

	}
	
}
