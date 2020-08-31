package com.rk.hystrix.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CaptureService {

	@HystrixCommand(fallbackMethod = "fallbackCapture", commandKey = "captureHystrixCmdKey")
	public String capture(long amt) {
		log.info("CaptureService|capture|Entry time : {}", LocalDateTime.now());
		final String ingenicoUri = "http://localhost:8080/ingenico/capture/"+amt;
		String pspName="ingenico";
		log.info("Comminucating with pspName : {}  {}",pspName, ingenicoUri);
		RestTemplate restTemplate = new RestTemplate();
		String resp = restTemplate.getForObject(ingenicoUri, String.class);
		
		log.info("CaptureService|capture|Exit time : {}", LocalDateTime.now());
		return resp;

	}
	
	public String fallbackCapture(long amt) {
		
		log.info("CaptureService|fallbackCapture|Entry amt={}, time : {}",amt, LocalDateTime.now());
		return "Can't procees capture request now. Try after some time !";

	}
}
