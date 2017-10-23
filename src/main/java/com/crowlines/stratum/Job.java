package com.crowlines.stratum;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Job
 * 
 * {"jsonrpc":"2.0",
 *  "method":"job",
 *  "params":{"job_id":"697850426007062",
 *            "target":"6f4d0900",
 *            "blob":"0606d6b587cf0535c78512754ddc1f3136f6da7f0562f22ff5d64e4c3587d0758a07aa2083fe4c00000000e141ab83923e5ee7b9ce0af50551179982853640cb2a0b1a4a8673130cee06f106"}}
 *            
 * @author Neil
 */
public class Job {

    @JsonProperty("job_id")
    public String jobId;
    
    public String target;
    
    public String blob;
    
    public Job() {
    }

    public Job(final String jobId, final String target, final String blob) {
        this.jobId = jobId;
        this.target = target;
        this.blob = blob;
    }
    
    @Override
    public String toString() {
        return "job_id:" + this.jobId;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        
        if (this == obj) {
            result = true;
        } else if (obj instanceof Job) {
            Job otherJob = (Job) obj;
            String otherJobId = otherJob.jobId;
            result = jobId.equals(otherJobId);
        }
        
        return result;
    }
}
