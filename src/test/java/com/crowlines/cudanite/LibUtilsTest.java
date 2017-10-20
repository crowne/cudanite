package com.crowlines.cudanite;

import jcuda.LibUtils;
import jcuda.runtime.JCuda;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class LibUtilsTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LibUtilsTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( LibUtilsTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        String libraryBaseName = "JCudaDriver-" + JCuda.getJCudaVersion();
        String libraryName = 
            LibUtils.createPlatformLibraryName(libraryBaseName);
        LibUtils.loadLibrary(libraryName);

    }
}
