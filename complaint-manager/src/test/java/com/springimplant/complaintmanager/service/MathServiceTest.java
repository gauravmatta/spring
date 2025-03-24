package com.springimplant.complaintmanager.service;

import com.springimplant.complaintmanager.service.impl.MathServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MathServiceTest {

    @InjectMocks
    MathServiceImpl mathService;

    @Test
    void testSumZero(){
        Integer i = mathService.addNumbers(0, 0);
        assertEquals(0,i);
    }

    @Test
    void testSumPositive(){
        Integer i = mathService.addNumbers(3, 7);
        assertEquals(10,i);
    }

    @Test
    void testSumNegative(){
        Integer i = mathService.addNumbers(-3, -7);
        assertEquals(-10,i);
    }

}
