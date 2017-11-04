package com.crowlines.stratum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crowlines.cudanite.CudaDevice;

public class Miner implements Runnable {
    
    private static final Logger LOG = LoggerFactory.getLogger(Miner.class);

    private boolean isActive;
    private Thread minerThread;
    private StratumClient stratumClient;
    private CudaDevice device;
    private Job job;
    private long nonce;
    
    /**
     * Implements a runnable mining thread.
     * @param stratumClient
     * @param device
     * @throws NullPointerException if stratumClient or device are null
     * @throws IllegalArgumentException if stratumClient is not logged in
     */
    public Miner(final StratumClient stratumClient, final CudaDevice device) {
        
        if ( stratumClient == null ) {
            throw new NullPointerException("stratumClient must not be null");
        }
        
        if ( device == null ) {
            throw new NullPointerException("device must not be null");
        }
        
        if ( !stratumClient.isLoggedIn() ) {
            throw new IllegalStateException("stratumClient must be logged in");
        }
        
        this.stratumClient = stratumClient;
        job = null;
        nonce = 0L;
        isActive = true;
        
        minerThread = new Thread(this);
        minerThread.start();
    }
    
    @Override
    public void run() {
        int loopCount = 0;
        while ( isActive ) {
            synchronized (stratumClient) {
                job = stratumClient.getJob();
            }
            loopCount++;
        }
        LOG.info("loopCount = " + loopCount);
    }
    
    public void shutdown() {
        synchronized (stratumClient) {
            isActive = false;
        }
    }

}
