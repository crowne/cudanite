package com.crowlines.cudanite;

public class CudaComputeCapability {
    
    public final int major;
    public final int minor;
    
    public CudaComputeCapability(final int major, final int minor) {
        this.major = major;
        this.minor = minor;
    }
    
    @Override
    public String toString() {
        return Integer.toString(major) + "." + Integer.toString(minor);
    }

}
