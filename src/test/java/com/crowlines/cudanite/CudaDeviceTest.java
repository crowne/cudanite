package com.crowlines.cudanite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CudaDeviceTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(CudaDeviceTest.class);

    private CudaDevice device; 

    @Before
    public void setUp() throws Exception {
        CudaDeviceFactory helper = new CudaDeviceFactory();
        device = helper.getDevice(0);
    }

    @Test
    public void testGetName() {
        String name = device.getName();
        Assert.assertNotNull("name is null", name);
        LOG.info("Name : " + name);
    }

    @Test
    public void testGetComputeCapatility() {
        CudaComputeCapability ccc = device.getComputeCapatility();
        Assert.assertNotNull("CudaComputeCapability is null", ccc);
        LOG.info("ComputeCapability : " + ccc.toString());

    }
    
    @Test
    public void testDescribe() {
        device.describe();
    }

}
