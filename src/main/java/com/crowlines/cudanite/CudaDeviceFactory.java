package com.crowlines.cudanite;

import static jcuda.driver.JCudaDriver.*;

import java.util.ArrayList;
import java.util.List;

import jcuda.driver.*;


/**
 * This class initializes the Cuda environment and provides access to GPU devices.
 */
public class CudaDeviceFactory {
    
	static {
        JCudaDriver.setExceptionsEnabled(true);
        cuInit(0);
	}
	
	private List<CudaDevice> deviceList;
	
	public CudaDeviceFactory() {
        // Obtain the number of devices
	    int deviceCountArray[] = { 0 };
	    JCudaDriver.cuDeviceGetCount(deviceCountArray);
	    
	    int deviceCount = deviceCountArray[0];
	    deviceList = new ArrayList<CudaDevice>(deviceCount);
	    
	    for (int i = 0; i < deviceCount; i++) {
            CUdevice device = new CUdevice();
            cuDeviceGet(device, i);
            CudaDevice cudaDevice = new CudaDevice(device);
            deviceList.add(cudaDevice);
        }
	}
	
	/**
	 * Returns the number of GPU's detected
	 * @return int
	 */
	public int getDeviceCount() {
		return deviceList.size();
	}
	
	/**
	 * Returns the list of devices
	 * @return List<CudaDevice>
	 */
	public List<CudaDevice> getDeviceList() {
	    return deviceList;
	}
	
    /**
     * Returns the nth CudaDevice
     * @param index
     * @return returns the nth CudaDevice
     * @throws IndexOutOfBoundsException when index is greater than getDeviceCount()
     */
	public CudaDevice getDevice(final int index) {
        CudaDevice device = deviceList.get(index);
        return device;
    }
}