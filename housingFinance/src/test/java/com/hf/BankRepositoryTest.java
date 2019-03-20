package com.hf;

import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hf.repository.BankRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankRepositoryTest {

	//생성한 Repository 
	@Autowired
	private BankRepository bankRepository;
	
	//테이블 생성 테스트
	@Test
	public void inspect() {
		//실제 객체 클래스 이름
		
		Class<?> clz = bankRepository.getClass();
		System.out.println(clz.getName());
		
		//인터페이스 목록
		Class<?>[] interfaces = clz.getInterfaces();
		Stream.of(interfaces).forEach(inter -> System.out.println(inter.getName()));
		
		//부모클래스
		Class<?> superClasses = clz.getSuperclass();
		System.out.println(superClasses.getName());
		
	}
	
}
