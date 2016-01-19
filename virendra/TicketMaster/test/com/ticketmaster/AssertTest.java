package com.ticketmaster;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by root on 18/1/16.
 */
public class AssertTest {

    @BeforeClass
    public static void runBeforeMethods(){
        System.out.println("\tThis test class is used to check the asserts available");
    }

    @Test
    @Ignore
    public void assertTestMethod1(){
        System.out.println("assertTestMethod1 test");

        //this test will fail because of below assert statement
        //this will be sorted in next test

//        assertEquals(<T> expected, <T> actual); T can be any primitive value or an object
//        other version of assertEquals is
//        assertEquals(String message, <T> expected, <T> actual);

        assertEquals("Demo text", "demo text");
        assertTrue(true);
    }


    @Test
    @Ignore
    public void assertTestMethod2(){
        System.out.println("assertTestMethod2 test");

        //this test case will pass
//        assertTrue(boolean condition);
        assertTrue(("demo text").equalsIgnoreCase("DEMO TexT"));
    }

    @Test
    @Ignore
    public void assertTestMethod3(){
        System.out.println("assertTestMethod3 test");

        //this shows how one can put custom message to the assert statement
//        assertTrue(String message, boolean condition);
        assertTrue("Custom Message generated after failure",("demo text").equals("DEMO TexT"));
    }

}
