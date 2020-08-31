package com.rk.hystrix.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rk.hystrix.dtos.BasePspsResponseDTO;
import com.rk.hystrix.services.CaptureService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CaptureController {

	@Autowired
	private CaptureService captureService;

	@GetMapping("/capture/{amt}")
	public ResponseEntity<BasePspsResponseDTO> capture(@PathVariable Long amt) {

		log.info("CaptureController|capture EntryTime : {}", LocalDateTime.now());
		String resp = captureService.capture(amt);

		log.info("CaptureController|capture ExitTime : {}", LocalDateTime.now());
		return ResponseEntity.ok(BasePspsResponseDTO.builder().status("SUCCESS").responseData(resp).build());

	}

}
