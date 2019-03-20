/**
 * 
 */
/**
 * @author sypark
 *
 */
package com.hf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.hf.domain.Bank;

@RepositoryRestResource
public interface BankRepository extends JpaRepository<Bank, Long>{
	
	//은행명으로 은행코드 찾기
	@RestResource
	List<Bank> findByBankName(String bankName);
	
	//은행코드로 은행명 찾기
	@RestResource
	List<Bank> findByBankCode(String bankCode);
}