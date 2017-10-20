package com.crowlines.stratum;

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

public class StratumClient {
	
	private static final Logger LOGGER = Logger.getLogger( StratumClient.class.getName() );
	
	private Socket socket;
	private JsonRpcClient jsonRpcClient;
	private StratumServer service;
	private String minerId;
	private Job job;
	
	public StratumClient(final String hostName, final int port) {
		try {
			socket = new Socket(InetAddress.getByName(hostName), port, null, 0);
			jsonRpcClient = new JsonRpcClient();
			service = ProxyUtil.createClientProxy(this.getClass().getClassLoader(), StratumServer.class, jsonRpcClient, socket);
			minerId = null;
			job = null;
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
	
	public LoginResult login(final String login, final String pass) {
        LoginRequest arguments = new LoginRequest();
        arguments.login = login;
        arguments.pass = pass;
        arguments.agent = "cudanite/1.0";
        
        LoginResult result = service.login(arguments);
        if ( result.status.equals("OK") ) {
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
	
}
