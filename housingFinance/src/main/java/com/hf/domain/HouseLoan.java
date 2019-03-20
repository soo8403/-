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
public class HouseLoan {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;
	
	@Column
	String bankCode;
	
	@Column
	private String yyyy;
	
	@Column
	private String mm;	
	
	@Column
	private Long amt;
	
	@Builder
	public HouseLoan(String bankCode, String yyyy, String mm, Long amt) {
		this.bankCode = bankCode;
		this.yyyy = yyyy;
		this.mm = mm;
		this.amt = amt;
	}
	
	@Builder
	public HouseLoan(String yyyy, Long amt) {
		this.yyyy = yyyy;
		this.amt = amt;
	}	
	
}