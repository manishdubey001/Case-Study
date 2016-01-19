package com.caseStudy;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by root on 16/1/16.
 */
public class CalculationTest {

	@Test
	public void testFindMax() throws Exception {
		assertEquals(4, Calculation.findMax(new int[]{1,4,3,2}));
		assertEquals(-1, Calculation.findMax(new int[]{-1,-5,-4,-3,-2}));
	}

	 @Test
    public void testCube(){
        System.out.println("test case cube");
        assertEquals(28,Calculation.cube(3));
    }

	@Test
    public void testReverseWord(){
        System.out.println("test case reverse word");
//        assertEquals("nitin",Calculation.reverseWords("nitin"));
		assertEquals("nahk si eman ym",Calculation.reverseWords("my name is khan"));
	}
}