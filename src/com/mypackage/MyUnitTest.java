package com.mypackage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MyUnitTest {

	@Before
	public void setUp(){
		
	}
	
	@Test
	public void test() {
		int i = MyTest.intMethod();
		assertEquals("Problem", 5, i);
	}

}
