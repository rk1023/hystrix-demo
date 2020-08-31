package com.rk.hystrix.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasePspsResponseDTO {

	private String status;
	private String responseData;
	private String message;
	private String responseTime;
	private String fallbackUrl;
	
	
}
