package com.crowlines.stratum.integration;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import javax.net.ServerSocketFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crowlines.stratum.Job;
import com.crowlines.stratum.LoginResult;
import com.crowlines.stratum.StratumClient;
import com.crowlines.stratum.server.StratumServerImpl;
import com.crowlines.stratum.server.StratumServer;
import com.googlecode.jsonrpc4j.JsonRpcBasicServer;
import com.googlecode.jsonrpc4j.StreamServer;

public class StratumTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(StratumTest.class);
    
    private static final int PORT = 3333;
    
    private static ServerSocket serverSocket;
    private static JsonRpcBasicServer jsonRpcServer;
    private static StratumServer service;
    private static StreamServer streamServer;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        LOG.debug("BeforeClass - Start");
        serverSocket = ServerSocketFactory.getDefault().createServerSocket(PORT, 0, InetAddress.getLocalHost());
        service = new StratumServerImpl();
        jsonRpcServer = new JsonRpcBasicServer(service, StratumServer.class);
        streamServer = new StreamServer(jsonRpcServer, 5, serverSocket);
        streamServer.start();
        LOG.debug("BeforeClass - End");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        if ( streamServer != null && streamServer.isStarted() ) {
            streamServer.stop();
        }
        if ( serverSocket != null ) {
            serverSocket.close();
        }
    }

    private StratumClient client;
    
    @Before
    public void setUp() throws Exception {
        LOG.debug("setUp - Start");
        this.client = new StratumClient(InetAddress.getLocalHost().getHostName() , PORT);
        LOG.debug("setUp - End");
    }

    @After
    public void tearDown() throws Exception {
        if ( this.client != null) {
            this.client.close();
        }
    }

    @SuppressWarnings("unused")
    @Test
    public void testLogin() throws UnknownHostException {
        LoginResult result = client.login("T3STr7ywKXq7n1gbNQbvoadQstiQw8qM2WN4wBANaFUfNA4EQXrR4VFiVtnmJJud3TDDyRfHju9NmJzjf6NiNX4BUB7r4Dv", "x");
        Assert.assertNotNull(result);
        Assert.assertEquals("login status not OK", "OK", result.status);
        Assert.assertTrue("Not logged in : " + client.getMinerId(), client.isLoggedIn() );
        LOG.info(result.toString());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetJobNoLogin() {
        Job job = client.getJob();
        Assert.assertNull("Shouldn't reach this point, due to no login", job);
        Assert.fail("Don't expect to reach this point");
    }

    @Test
    public void testGetJob() {
        client.login("T3STr7ywKXq7n1gbNQbvoadQstiQw8qM2WN4wBANaFUfNA4EQXrR4VFiVtnmJJud3TDDyRfHju9NmJzjf6NiNX4BUB7r4Dv", "x");
        Job job = client.getJob();
        Assert.assertNotNull("job should not be null", job);
        LOG.info("jobId:" + job.jobId);
    }

}
