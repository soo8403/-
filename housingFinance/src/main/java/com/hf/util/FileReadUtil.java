package com.hf.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileReadUtil {

	//CSV 파일 읽기 
	public List<Map <Integer, String>> csvReader(String targetFile)  throws Exception {
		
		List<Map <Integer, String>> rowList = new ArrayList<>();
		String line = "";
		String cvsSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(targetFile), "EUC-KR"))) {
			while((line=br.readLine()) != null) {
				Map<Integer, String> map = new HashMap<Integer, String>();
				int i = 0;

				String [] data = line.split(cvsSplitBy, -1);
				
				for (String string : data) {
					string = string.replaceAll("[{}\",]*", ""); 
					map.put(i, string);
					//System.out.println("data : " + string+"["+i+"]");
					i++;
				}
				
				rowList.add(map);
			}
		} catch (IOException e) {
			throw new Exception(e);
		}
		
		return rowList;
		
	
	}
}
