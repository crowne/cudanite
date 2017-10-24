package com.crowlines.stratum;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.crowlines.stratum.server.StratumServer;
import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.ProxyUtil;

public class StratumClient implements Closeable {
	
	private static final Logger LOGGER = Logger.getLogger( StratumClient.class.getName() );
	
	private Socket socket;
	private JsonRpcClient jsonRpcClient;
	private StratumServer service;
	private String minerId;
	private Job job;
	private long target;
	
	public StratumClient(final String hostName, final int port) {
		try {
			socket = new Socket(InetAddress.getByName(hostName), port, null, 0);
			jsonRpcClient = new JsonRpcClient();
			service = ProxyUtil.createClientProxy(this.getClass().getClassLoader(), StratumServer.class, jsonRpcClient, socket);
			minerId = null;
			job = null;
			target = 0;
		} catch (MalformedURLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (UnknownHostException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
	}
	
    @Override
    public void close() throws IOException {
        try {
            job = null;	    
            minerId = null;
            if ( socket != null ) {
                socket.close();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
	}
	
	public LoginResult login(final String login, final String pass) {
        LoginRequest arguments = new LoginRequest();
        arguments.login = login;
        arguments.pass = pass;
        arguments.agent = "cudanite/1.0";
        
        LoginResult result = service.login(arguments);
        if ( result != null && "OK".equals(result.status) ) {
            this.minerId = result.id;
            this.job = result.job;
        }
        
        return result;
	}
	
	public boolean isLoggedIn() {
	    boolean isLoggedIn = false;
	    if ( this.minerId != null ) {
	        isLoggedIn = true;
	    }
	    return isLoggedIn;
	}
	
	public String getMinerId() {
	    return this.minerId;
	}
	
	public Job getJob() {
	    Job newJob = null;
	    
	    if ( !this.isLoggedIn() ) {
	        throw new IllegalStateException("Not logged in");
	    }
	    
	    newJob = service.getjob(this.minerId);
        if ( newJob.isValid() ) {
    	    if ( !newJob.equals(this.job) ) {
                LOGGER.log(Level.INFO, "New Job detected : " + newJob.jobId );
                this.job = newJob;
    	    }
            
    	    long newTarget = Long.decode("0x" + newJob.target ).longValue();
            if (target != newTarget) {
                target = newTarget;
                double difficulty = (((double) 0xffffffff) / target);
                LOGGER.log(Level.INFO, "Pool set difficulty to : " + difficulty );
            }
        }
	    
	    return job;
	}

}
