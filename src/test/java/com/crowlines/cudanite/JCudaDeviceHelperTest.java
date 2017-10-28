package com.crowlines.cudanite;

import org.junit.Test;

public class JCudaDeviceHelperTest {

    private static final String JAVA_LIBRARY_PATH = "java.library.path";

	@Test
	public void testMain() {
		String javaLibPath = System.getProperty(JAVA_LIBRARY_PATH);
		System.out.println(JAVA_LIBRARY_PATH + " = " + javaLibPath); 
		JCudaDeviceHelper.main(null);
	}

}
