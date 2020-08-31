package com.rk.hystrix.controllers;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.rk.hystrix.dtos.BasePspsResponseDTO;
import com.rk.hystrix.services.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PaymentController {

	
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("/payments")
	public ResponseEntity<BasePspsResponseDTO> payment() {

		log.info("PaymentController|payment EntryTime : {}", LocalDateTime.now());

		List<String> pspList=new ArrayList<>(2);
		pspList.add("INGENICO");
		pspList.add("PAYPAL");
		
		String fallbackUrl="http://localhost:8081/paypal/payment";
		BasePspsResponseDTO dto=BasePspsResponseDTO.builder().message("Test Fallback methods").status("SUCCESS").fallbackUrl(fallbackUrl).build();
		String resp = paymentService.payment(dto);

		log.info("PaymentController|payment ExitTime : {}", LocalDateTime.now());
		return ResponseEntity.ok(BasePspsResponseDTO.builder().status("SUCCESS")
				.responseData(resp).build());

	}
	
	@GetMapping("/payments/noHystrix")
	public ResponseEntity<BasePspsResponseDTO> paymentNoHystrix() {

		log.info("PaymentController|payment EntryTime : {}", LocalDateTime.now());

		String resp = paymentService.paymentWithoutHystrix();

		log.info("PaymentController|payment ExitTime : {}", LocalDateTime.now());
		return ResponseEntity.ok(BasePspsResponseDTO.builder().status("SUCCESS")
				.responseData(resp).build());

	}
}
