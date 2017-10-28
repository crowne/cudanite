package com.crowlines.stratum.server;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crowlines.stratum.Job;
import com.crowlines.stratum.LoginRequest;
import com.crowlines.stratum.LoginResult;

public class StratumServerImpl implements StratumServer {
    
    
    private static final Logger LOG = LoggerFactory.getLogger(StratumServerImpl.class);
    private static final String PROP_KEY_IS_TEST = "isTest";
    
    private Properties props;
    private boolean isTest = false;
    
    
    public StratumServerImpl() {
        props = new Properties();
        try (final InputStream stream = StratumServerImpl.class.getClassLoader().getResourceAsStream("stratum-test.properties")) {
            if (stream != null) {
                props.load(stream);
                this.isTest = "true".equalsIgnoreCase(props.getProperty(PROP_KEY_IS_TEST, "false"));
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        
        if ( props.isEmpty() ) {
            try (final InputStream stream = StratumServerImpl.class.getClassLoader().getResourceAsStream("stratum.properties")) {
                if (stream != null) {
                    props.load(stream);
                    this.isTest = "true".equalsIgnoreCase(props.getProperty(PROP_KEY_IS_TEST, "false"));
                }
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
    }
    
/*
    private String generateUid() {
        BigInteger min = new BigInteger("100000000000000");
        BigInteger max = new BigInteger("999999999999999");
        
//        BigInteger id = max.subtract(min).add(BigInteger.ONE).multiply(BigInteger.valueOf( Math.random() ));
        
        return "";
        
// JavaScript version
//        uid = function(){
//            var min = 100000000000000;
//            var max = 999999999999999;
//            var id = Math.floor(Math.random() * (max - min + 1)) + min;
//            return id.toString();
//        };
    }
*/
    
    @Override
    public LoginResult login(final LoginRequest arguments) {
        LoginResult result = null;
        
        if ( this.isTest ) {
            LOG.debug("creating Test LoginResult");
            result = StratumTestFactory.createLoginResult();
        }
        
        return result;
    }

    @Override
    public Job getjob(final String id) {
        Job job = null;
        
        if ( this.isTest ) {
            LOG.debug("creating Test Job");
            job = StratumTestFactory.createJob();
        }
        
        return job;
    }
    
}
