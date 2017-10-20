package com.crowlines.stratum.server;

import com.crowlines.stratum.Job;
import com.crowlines.stratum.LoginResult;

public class StratumTestFactory {

    public static Job createJob(final String jobId, final String target, final String blob) {
        Job job = new Job();
        job.jobId = jobId;
        job.target = target;
        job.blob = blob;
        return job;
    }

    public static Job createJob() {
        String jobId = "697850426007062";
        String target = "6f4d0900";
        String blob = "0606d6b587cf0535c78512754ddc1f3136f6da7f0562f22ff5d64e4c3587d0758a07aa2083fe4c00000000e141ab83923e5ee7b9ce0af50551179982853640cb2a0b1a4a8673130cee06f106";
        
        Job job = StratumTestFactory.createJob(jobId, target, blob);
        
        return job;
    }

    public static LoginResult createLoginResult() {
        Job job = createJob();
        LoginResult result = new LoginResult("686739698913879", job, "OK");
        return result;
    }

}
