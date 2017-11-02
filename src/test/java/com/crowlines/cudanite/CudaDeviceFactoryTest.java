package com.crowlines.cudanite;

import org.junit.Assert;
import org.junit.Test;

public class CudaDeviceFactoryTest {

	@Test
	public void testGetDeviceCount() {
		CudaDeviceFactory helper = new CudaDeviceFactory();
		Assert.assertEquals("expected 1 device", 1, helper.getDeviceCount() );
	}

    @Test
    public void testGetDevice() {
        CudaDeviceFactory helper = new CudaDeviceFactory();
        CudaDevice device = null;
        
        device = helper.getDevice(0);
        Assert.assertNotNull("device is null", device);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetDeviceError() {
        CudaDeviceFactory helper = new CudaDeviceFactory();
        CudaDevice device = null;
        
        device = helper.getDevice(1);
        Assert.assertNull("We should not reach this point", device);
    }
}
