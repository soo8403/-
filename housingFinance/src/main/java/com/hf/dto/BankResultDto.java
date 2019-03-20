package com.hf.dto;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BankResultDto {
	
	private String year;
	private Double totalAmt;
	private Map<String, Double> detailAmt;
	private String bank;
}
