package com.ticketmaster;

import org.junit.*;

/**
 * This class is used to check the flow in test case execution
 * Created by root on 15/1/16.
 */
public class FirstTestClass {

    @BeforeClass
    public static void callAfterClassStart() {
        System.out.println("==> @BeforeClass Annotation======\n");


    }


    /**
     *
     */
    @After
    public void callAfterTest(){
        System.out.println("==> @After Annotation======\n");

    }

    /**
     *
     */
    @AfterClass
    public static void callBeforeClassEnd(){
        System.out.println("==> @AfterClass Annotation======\n");

    }

    /**
     *
     */
    @Before
    public void callBeforeTest(){
        System.out.println("==> @Before Annotation======\n");

    }

    /**
     *
     */
    @Test
    @Ignore
    public void testMethod1(){
        System.out.println("First Test block. Method executed is testMethod1");


    }

    /**
     *
     */
    @Test
    @Ignore
    public void testMethod2(){
        System.out.println("Second Test block. Method executed is testMethod2");
    }

}
