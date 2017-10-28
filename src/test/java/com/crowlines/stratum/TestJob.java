package com.crowlines.stratum;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crowlines.stratum.server.StratumTestFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJob {

    
    private static final Logger LOG = LoggerFactory.getLogger(TestJob.class);

    @Test
    public void testJob() throws JsonProcessingException {
        Job job = StratumTestFactory.createJob();
        ObjectMapper mapper = new ObjectMapper();
        
        String json = mapper.writeValueAsString(job);
        
        LOG.info(json);

        String pattern = "^\\{\"target\":.*,\"blob\":.*,\"job_id\":.*\\}$";
        
        Assert.assertTrue("Job does not match regexp " + pattern + " : " + json, json.matches(pattern));
    }

    @Test
    public void testEquals() throws JsonProcessingException {
        Job job1 = StratumTestFactory.createJob();
        Job job2 = StratumTestFactory.createJob();
        
        Assert.assertEquals("Same job must be equal", job1, job1);
        Assert.assertEquals("Jobs are not equal", job1, job2);
        Assert.assertNotEquals("Cannot be equal to null", job1, null);
    }

    @Test
    public void testIsValid() throws JsonProcessingException {
        Job job = null;
        String jobId = "697850426007062";
        String target = "6f4d0900";
        String blob = "0606d6b587cf0535c78512754ddc1f3136f6da7f0562f22ff5d64e4c3587d0758a07aa2083fe4c00000000e141ab83923e5ee7b9ce0af50551179982853640cb2a0b1a4a8673130cee06f106";

        job = StratumTestFactory.createJob(jobId, target, blob);
        Assert.assertTrue("Job should be valid", job.isValid() );
        
        job = StratumTestFactory.createJob(null, target, blob);
        Assert.assertFalse("Invalid : null jobId", job.isValid() );
        
        job = StratumTestFactory.createJob(jobId, null, blob);
        Assert.assertFalse("Invalid : null target", job.isValid() );

        job = StratumTestFactory.createJob(jobId, target, null);
        Assert.assertFalse("Invalid : null blob", job.isValid() );

        job = StratumTestFactory.createJob(jobId, target, "12345678901234567890123456789012345678901234567890A");
        Assert.assertFalse("Invalid : Odd length blob", job.isValid() );

        job = StratumTestFactory.createJob(jobId, target, "123");
        Assert.assertFalse("Invalid : Short length blob", job.isValid() );
    }

}
