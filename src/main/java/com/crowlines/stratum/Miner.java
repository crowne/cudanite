package com.crowlines.stratum;

public class Miner implements Runnable {

    private StratumClient stratumClient;
    private Job job;
    private long nonce;
    
    public Miner(final StratumClient stratumClient) {
        if ( stratumClient == null ) {
            throw new IllegalArgumentException("stratumClient must not be null");
        }
        if ( !stratumClient.isLoggedIn() ) {
            throw new IllegalStateException("stratumClient must not be logged in");
        }
        this.stratumClient = stratumClient;
        job = null;
        nonce = 0L;
    }
    
    @Override
    public void run() {
        job = stratumClient.getJob();

    }

}
