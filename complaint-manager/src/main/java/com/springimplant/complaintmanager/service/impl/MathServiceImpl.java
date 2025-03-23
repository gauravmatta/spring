package com.springimplant.complaintmanager.service.impl;

import com.springimplant.complaintmanager.service.MathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("MathServiceImpl")
@Slf4j
public class MathServiceImpl implements MathService {
	
	@Override
	public Integer addNumbers(Integer firstNumber,Integer secondNumber) {
		log.info("Math Service Called. Add Numbers");
		log.info("First: "+ firstNumber);
		log.info("Second: "+ secondNumber);
		return firstNumber + secondNumber;
	}
	

}
