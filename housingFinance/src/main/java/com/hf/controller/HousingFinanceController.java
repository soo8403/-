/**
 * 
 */
/**
 * @author sypark
 *
 */
package com.hf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HousingFinanceController {
	
	@RequestMapping("/")
	public String index() {
		return "HousingFinance";
	}
}