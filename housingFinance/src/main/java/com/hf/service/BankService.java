package com.hf.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hf.domain.Bank;
import com.hf.domain.HouseLoan;
import com.hf.dto.BankResultDto;
import com.hf.repository.BankRepository;
import com.hf.repository.HouseLoanRepository;
import com.hf.util.FileReadUtil;



@Service
public class BankService {
	
	private  BankRepository bankRepository;
	private  HouseLoanRepository houseLoanRepository;
	
	
	public BankService(BankRepository bankRepository, HouseLoanRepository houseLoanRepository) { 
		this.bankRepository =bankRepository;
		this.houseLoanRepository = houseLoanRepository;
	}
	 
	//Q1. cvs 데이터 읽고 저장하기 
	public List<Map<Integer, String>> readCsvFile(String file) {

		List<Map<Integer, String>> readList = new ArrayList<>();
		FileReadUtil util = new FileReadUtil();
		try {
			readList = util.csvReader(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return readList;
	}

	//Q2. 은행목록 출력
	public String getBankList() {
		List<Bank> resultList = bankRepository.findAll();
		//Gson 생성
		Gson gson = new GsonBuilder().create();
		//Json 변경
		String resultJson = gson.toJson(resultList); 
		//System.out.println("json by "+resultJson);
		return resultJson;
	}
			
	//Q3. 연도별 각 금융기관의 지원금액 합계를 출력하는 API
	public String getTotalSumAmtByYear() {
		
		//전체 리스트
		List<HouseLoan> result = houseLoanRepository.findAll();
		
		//1.연도별 총합계
		Map<String, Double> totalSumAmtByYear =
				result.stream().collect(Collectors.groupingBy(HouseLoan::getYyyy
											,Collectors.summingDouble(HouseLoan::getAmt)));
		
		//2. 연도별 은행별 합계 
		Map<String, Map<String, Double>> detailSumAmtByYear =
				result.stream().collect(Collectors.groupingBy(HouseLoan::getYyyy
											,Collectors.groupingBy(HouseLoan::getInstituteCode, Collectors.summingDouble(HouseLoan::getAmt))));
		//1과 2를 재조합
		List resultList = new ArrayList();
		for( String key : totalSumAmtByYear.keySet() ){ 
			
			BankResultDto dto = new BankResultDto();
			dto.setYear(key);
			dto.setTotalAmt(totalSumAmtByYear.get(key));
			
			for ( String key2 : detailSumAmtByYear.keySet() ){
				if (key.equals(key2)) {
					dto.setDetailAmt(detailSumAmtByYear.get(key2));
				}
			}
			
			resultList.add(dto);
		}

		//Gson 생성
		Gson gson = new GsonBuilder().create();
		//Json 변경
		String resultJson = gson.toJson(resultList); 
		//System.out.println("json by "+resultJson);
		return resultJson;
	}
	
	//Q4. 각 연도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관
	public String getMaxAmtBank() {
		
		//전체 리스트
		List<HouseLoan> result = houseLoanRepository.findAll();
		
		//연도별 은행별 합계 
		Map<String, Map<String, Double>> detailSumAmtByYear =
				result.stream().collect(Collectors.groupingBy(HouseLoan::getYyyy
											,Collectors.groupingBy(HouseLoan::getInstituteCode, Collectors.summingDouble(HouseLoan::getAmt))));
		
		Double max = 0.0;  //최대깂 초기화
		BankResultDto dto = null;
		for ( String key : detailSumAmtByYear.keySet()){
			//오름차순 정렬 
			Iterator it = sortByValue(detailSumAmtByYear.get(key)).iterator();
			
			String bankCd = (String)it.next(); //첫번째 
			String bankNm = getBankName(bankCd);
			String year = key;
			Double totalAmt = (detailSumAmtByYear.get(key)).get(bankCd);
			
			if (totalAmt > max) {
				max = totalAmt;
				dto = new BankResultDto();
				dto.setYear(year);
				dto.setBank(bankNm);
				//dto.setTotalAmt(totalAmt);
			}
		}
		
		//Gson 생성
		Gson gson = new GsonBuilder().create();
		//Json 변경
		String resultJson = gson.toJson(dto); 
		//System.out.println("json by "+resultJson);
		return resultJson;
	}
	
	//Q5. 외환은행의 연도별 금액합계의 평균
	public String getAvgYearByBankList(String sBankName) {
		
		String resultJson = "";
		//은행명으로 은행코드 조회
		List<Bank> findBank = bankRepository.findByBankName(sBankName);
		String backCode = findBank.get(0).getBankCode();
		
		//은행명이 존재하지 않으면 
		if (backCode == "" || backCode == null ) {
			resultJson = "은행명이 존재하지 않습니다.";
			return resultJson;
		}
		
		//전체 리스트
		List<HouseLoan> list = houseLoanRepository.findAll();
		
		//입력한 은행의 평균지원금액 
		Map<String, Double> avgYearByBank 
			= list.stream().filter(houseloan->houseloan.getInstituteCode().equals(backCode))
						   .collect(Collectors.groupingBy(HouseLoan::getYyyy
										,Collectors.averagingDouble(HouseLoan::getAmt))
							);
		//오름차순 정렬
        Iterator it = sortByValue(avgYearByBank).iterator();
       
        String minRank = ""; //최소값
        String maxRank = (String)it.next(); //최대값
        while(it.hasNext()) {
        	if (it.hasNext()) {
        		minRank = (String)it.next();
        	}
        }
        
        Map<String, Double> avgMap = new HashMap();
        avgMap.put(maxRank, avgYearByBank.get(maxRank));
        avgMap.put(minRank, avgYearByBank.get(minRank));
        
        BankResultDto dto = new BankResultDto();
        dto.setBank(sBankName);
        dto.setDetailAmt(avgMap);
        
		//Gson 생성
		Gson gson = new GsonBuilder().create();
		//Json 변경
		resultJson = gson.toJson(dto); 
        
        //System.out.println(maxRank + " = " + avgYearByBank.get(maxRank));  //최대
        //System.out.println(minRank + " = " + avgYearByBank.get(minRank));  //최소
   
		return resultJson;
	}
	
	//정렬 
	public static List sortByValue(final Map map) {

        List<String> list = new ArrayList();
        list.addAll(map.keySet());

        Collections.sort(list,new Comparator() {
            public int compare(Object o1,Object o2) {

                Object v1 = map.get(o1);
                Object v2 = map.get(o2);

                return ((Comparable) v2).compareTo(v1);
            }
        });

        //Collections.reverse(list); // 주석시 오름차순
        return list;
    }
	
	//은행코드로 은행명 찾기
	public String getBankName(String sBankCode) {
		
		List<Bank> findBank = this.bankRepository.findByBankCode(sBankCode);
		String backName = findBank.get(0).getBankName();

		return backName;
	}



	
	
	

}
