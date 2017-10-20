package com.crowlines.stratum.integration;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.net.ServerSocketFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.crowlines.stratum.LoginResult;
import com.crowlines.stratum.StratumClient;
import com.crowlines.stratum.server.StratumServerImpl;
import com.crowlines.stratum.server.StratumServer;
import com.googlecode.jsonrpc4j.JsonRpcBasicServer;
import com.googlecode.jsonrpc4j.StreamServer;

import junit.framework.TestCase;

public class StratumTest extends TestCase {

    private static final Logger LOGGER =  Logger.getLogger( StratumTest.class.getName() );
    
    private static final int PORT = 3333;
    
    private ServerSocket serverSocket;
    private JsonRpcBasicServer jsonRpcServer;
    private StratumServer service;

    @Before
    public void setUp() throws Exception {
        serverSocket = ServerSocketFactory.getDefault().createServerSocket(PORT, 0, InetAddress.getLocalHost());
        service = new StratumServerImpl();
        jsonRpcServer = new JsonRpcBasicServer(service, StratumServer.class);
    }

    @SuppressWarnings("unused")
    @Test
    public void testLogin() throws UnknownHostException {
        StreamServer streamServer = createAndStartServer();
        StratumClient client = new StratumClient(InetAddress.getLocalHost().getHostName() , PORT);
        LoginResult result = client.login("T3STr7ywKXq7n1gbNQbvoadQstiQw8qM2WN4wBANaFUfNA4EQXrR4VFiVtnmJJud3TDDyRfHju9NmJzjf6NiNX4BUB7r4Dv", "x");
        Assert.assertNotNull(result);
        Assert.assertEquals("login status not OK", "OK", result.status);
        Assert.assertTrue("Not logged in : " + client.getMinerId(), client.isLoggedIn() );
        LOGGER.info(result.toString());
    }

    private StreamServer createAndStartServer() {
        StreamServer streamServer = new StreamServer(jsonRpcServer, 5, serverSocket);
        streamServer.start();
        return streamServer;
    }

}
