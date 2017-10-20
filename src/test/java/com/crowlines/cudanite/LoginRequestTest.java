package com.crowlines.cudanite;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.crowlines.stratum.LoginRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginRequestTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        
        LoginRequest arguments = new LoginRequest();
        arguments.login = "42KRr7ywKXq7n1gbNQbvoadQstiQw8qM2WN4wBANaFUfNA4EQXrR4VFiVtnmJJud3TDDyRfHju9NmJzjf6NiNX4BUB7r4Dv";
        arguments.pass = "x";
        arguments.agent = "cudanite/0.1";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.valueToTree(arguments);
        System.out.println(node.toString());
        
        Assert.assertNotNull(arguments);
    }

}
