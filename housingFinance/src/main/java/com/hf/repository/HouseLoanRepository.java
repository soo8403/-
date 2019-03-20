/**
 * 
 */
/**
 * @author sypark
 *
 */
package com.hf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.hf.domain.HouseLoan;

@RepositoryRestResource
public interface HouseLoanRepository extends JpaRepository<HouseLoan, Long>{
}