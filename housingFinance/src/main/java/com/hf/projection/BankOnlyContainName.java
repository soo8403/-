package com.hf.projection;

import org.springframework.data.rest.core.config.Projection;
import com.hf.domain.Bank;

//은행명만 노출하도록 @Projection 설정 
@Projection(name = "getOnlyName", types = {Bank.class})
public interface BankOnlyContainName {
	
	String getBankName();
}
