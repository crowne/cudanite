package com.crowlines.stratum;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crowlines.cudanite.CudaDevice;
import com.crowlines.cudanite.CudaDeviceFactory;
import com.crowlines.stratum.server.StratumServer;
import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.ProxyUtil;

public class StratumClient implements Closeable {
    
    private static final Logger LOG = LoggerFactory.getLogger(StratumClient.class);

    private Socket socket;
	private JsonRpcClient jsonRpcClient;
	private StratumServer service;
	private String login;
	private String password;
	private String minerId;
	private Job job;
	private long target;
	private CudaDeviceFactory cudaFactory;
	private List<Miner> minerList;
	
	public StratumClient(final String hostName, final int port, final String login, final String password) {
		try {
			socket = new Socket(InetAddress.getByName(hostName), port, null, 0);
			jsonRpcClient = new JsonRpcClient();
			service = ProxyUtil.createClientProxy(this.getClass().getClassLoader(), StratumServer.class, jsonRpcClient, socket);
			minerId = null;
			job = null;
			target = 0;
			cudaFactory = new CudaDeviceFactory();
            this.login = login;
            this.password = password;
            this.login();
			
			int deviceCount = cudaFactory.getDeviceCount();
			minerList = new ArrayList<Miner>(deviceCount);
			
			for (int i = 0; i < deviceCount; i++) {
                CudaDevice device = cudaFactory.getDevice(i);
                device = cudaFactory.getDevice(i);
                Miner miner = new Miner(this, device);
                minerList.add(miner);
                LOG.info("Added miner #" + i);
            }
			
		} catch (MalformedURLException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (UnknownHostException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
	}
	
    @Override
    public void close() throws IOException {
        try {
            for (Iterator<Miner> iterator = minerList.iterator(); iterator.hasNext();) {
                Miner miner = (Miner) iterator.next();
                miner.shutdown();
            }
            job = null;
            minerId = null;
            if ( socket != null ) {
                socket.close();
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
	}
	
    public LoginResult login() {
        LoginRequest arguments = new LoginRequest();
        arguments.login = this.login;
        arguments.pass = this.password;
        arguments.agent = "cudanite/1.0";
        
        LoginResult result = service.login(arguments);
        if ( result != null && "OK".equals(result.status) ) {
            this.minerId = result.id;
            this.job = result.job;
        }
        
        return result;
    }
	
    public LoginResult login(final String login, final String password) {
        this.login = login;
        this.password = password;
        
        return this.login();
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
    	        LOG.info("New Job detected : " + newJob.jobId);
                this.job = newJob;
    	    }
            
    	    long newTarget = Long.decode("0x" + newJob.target ).longValue();
            if (target != newTarget) {
                target = newTarget;
                double difficulty = (((double) 0xffffffff) / target);
                LOG.info("Pool set difficulty to : " + difficulty);

            }
        }
	    
	    return job;
	}

}
