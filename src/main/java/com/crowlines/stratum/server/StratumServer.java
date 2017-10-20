package com.crowlines.stratum.server;

import com.crowlines.stratum.Job;
import com.crowlines.stratum.LoginRequest;
import com.crowlines.stratum.LoginResult;

public interface StratumServer {

    LoginResult login(LoginRequest arguments);
    
    Job getjob(String id);

}