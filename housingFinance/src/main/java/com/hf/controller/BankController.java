package com.hf.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hf.domain.Bank;
import com.hf.domain.HouseLoan;
import com.hf.repository.BankRepository;
import com.hf.repository.HouseLoanRepository;
import com.hf.service.BankService;

@RestController
@RequestMapping("/api/banks")
public class BankController {
	
	@Autowired
	BankService bankService;
	
	@Autowired
	BankRepository bankRepository;
	
	@Autowired
	HouseLoanRepository houseLoanRepository;	
	
	@RequestMapping(value = "/q2", method = RequestMethod.POST) 
	public ResponseEntity<String> importData() {
		
		File path = new File("");
		String filePath = path.getAbsolutePath().replace('\\', '/')+"/src/main/java/com/hf/util/test.csv";
		 System.out.println(filePath);
		List<Map <Integer, String>> importList = bankService.readCsvFile(filePath);
		
		for(int i=0; i<importList.size(); i++) {
			Map <Integer, String> bankMap = importList.get(i);
			if (i == 0) {
				//은행코드 입력
				for(int j=2; j<11; j++) {
					String instituteFullName = bankMap.get(j);
					String instituteName = instituteFullName.split("\\(")[0];
					String instituteCode = "bnk00"+j;
					
					bankRepository.save(new Bank(instituteCode, instituteName));
					//System.out.println(instituteCode+"|"+instituteName);
				}
			} else {
				//연도별 각 금융기관의 지원금액 입력		
				String yyyy = bankMap.get(0);
				String mm = bankMap.get(1);
				
				if (mm.length() < 2) {
					mm = "0"+mm;
				}
				
				for(int k=2; k<11; k++) {
					String bankCode = "bnk00"+k;
					String sAmt = bankMap.get(k).replace("\"", "");
					Long amt = Long.valueOf(sAmt);
					
					houseLoanRepository.save(new HouseLoan(bankCode, yyyy, mm, amt));
					//System.out.println(bankCode+"|"+yyyy+"/"+mm+"|"+amt);
				}
			}
		}
		return ResponseEntity.ok("Success Import Data");
	}	
	
	@RequestMapping(value = "/q2", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getBankList(){
		String result = bankService.getBankList();
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(value = "/q3", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getTotalSumAmtByYear(){
		String result = bankService.getTotalSumAmtByYear();
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(value = "/q4", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getMaxAmtBank(){
		String result = bankService.getMaxAmtBank();
		return ResponseEntity.ok(result);
	}	
	
	
	@RequestMapping(value = "/q5/{bankName}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> avgYearByBankList(@PathVariable(value = "bankName") String bankName){
		String result = bankService.getAvgYearByBankList(bankName);
		return ResponseEntity.ok(result);
	}		
	
	

	






	

}
