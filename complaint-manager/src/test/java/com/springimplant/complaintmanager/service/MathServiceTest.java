package com.springimplant.complaintmanager.service;

import com.springimplant.complaintmanager.service.impl.MathServiceImpl;
import com.springimplant.complaintmanager.util.MathUtil;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Bool;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class MathServiceTest {

    @InjectMocks
    MathServiceImpl mathService;

    @Mock
    MathUtil mathUtil;

    @BeforeAll
    public static void Init(){
        System.out.println("BeforeAll");
    }

    @BeforeEach
    public void initEachTest(){
        System.out.println("Before Each");
    }

    @Test
    void testPrivateMethod_validateProductName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method addNumbers = MathServiceImpl.class.getDeclaredMethod("checkNegative", Integer.class);
        addNumbers.setAccessible(true);
        Boolean invoke = (Boolean) addNumbers.invoke(mathService, Integer.valueOf(-1));
        assertEquals(Boolean.TRUE,invoke);
    }

    @Test
    void testSumZero(){
        Integer i = mathService.addNumbers(0, 0);
//        doNothing().when(mathUtil).setup();
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

    @AfterAll
    public static void Destroy(){
        System.out.println("After All");
    }

    @AfterEach
    public void cleanUp(){
        System.out.println("After Each");
    }

}
