/**
 * 
 */
/**
 * @author sypark
 *
 */
package com.hf.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Bank {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;
	
	@Column
	private String bankCode;
	
	@Column
	private String bankName;
	
	@Builder
	public Bank(String bankCode, String bankName) {
		this.bankCode = bankCode;
		this.bankName = bankName;
	}
	
}