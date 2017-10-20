package com.crowlines.stratum;

/**
 * Represents a LoginResult
 * 
 * {"id":1,
 *  "jsonrpc":"2.0",
 *  "error":null,
 *  "result":{"id":"686739698913879",
 *            "job":{"job_id":"456774535030126",
 *                   "target":"7b5e0400",
 *                   "blob":"0606d2b187cf0501d20173518e6798426879b42639a21d0060c177492d3476c4263560c354eac000000000087c8e60725794bfac010cc42ccdf9df6c388f4d37f8ef67b53e3a90a7474a6103"
 *                  },
 *            "status":"OK"}
 *           }
 *            
 * @author Neil
 */
public class LoginResult {

    public String id;
    
    public Job job;
    
    public String status;
    
    public LoginResult() {
    }

    public LoginResult(final String id, final Job job, final String status) {
        this.id = id;
        this.job = job;
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "LoginResult: status:" + status + " id:" + id;
    }
}
