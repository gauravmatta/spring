package com.springimplant.complaintmanager.service.impl;

import com.springimplant.complaintmanager.service.MathService;
import com.springimplant.complaintmanager.util.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("MathServiceImpl")
@Slf4j
public class MathServiceImpl implements MathService {

	@Autowired
	MathUtil mathUtil;
	
	@Override
	public Integer addNumbers(Integer firstNumber,Integer secondNumber) {
		log.info("Math Service Called. Add Numbers");
		log.info("First: "+ firstNumber);
		log.info("Second: "+ secondNumber);
		mathUtil.setup();
		if(checkNegative(firstNumber) || checkNegative(secondNumber)){
			log.info("One of the numbers is negative");
		}
		return (Integer) (firstNumber + secondNumber);
	}

	private boolean checkNegative(Integer a){
		return a < 0;
	}
}
