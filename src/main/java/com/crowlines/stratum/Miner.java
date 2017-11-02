package com.crowlines.stratum;

import com.crowlines.cudanite.CudaDevice;

public class Miner implements Runnable {

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
        while ( isActive ) {
            job = stratumClient.getJob();
        }

    }
    
    public void shutdown() {
        isActive = false;
    }

}
