package com.crowlines.cudanite;

import junit.framework.TestCase;

public class JCudaDeviceQueryTest extends TestCase {

	private static final String JAVA_LIBRARY_PATH = "java.library.path";

	public void testMain() {
		String javaLibPath = System.getProperty(JAVA_LIBRARY_PATH);
		System.out.println(JAVA_LIBRARY_PATH + " = " + javaLibPath); 
		JCudaDeviceQuery.main(null);
	}

}
