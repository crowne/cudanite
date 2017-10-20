package com.crowlines.stratum.server;

import java.util.logging.Logger;

import com.crowlines.stratum.Job;
import com.crowlines.stratum.LoginRequest;
import com.crowlines.stratum.LoginResult;

public class StratumServerImpl implements StratumServer {
    
    private static final Logger LOGGER = Logger.getLogger(StratumServerImpl.class.getName());
    
    @Override
    public LoginResult login(final LoginRequest arguments) {
        LoginResult result = null;
        
        if ( arguments.login.startsWith("T3ST") ) {
            LOGGER.fine("creating Test LoginResult");
            result = StratumTestFactory.createLoginResult();
        }
        
        return result;
    }

    @Override
    public Job getjob(final String id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
