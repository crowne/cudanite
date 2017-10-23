package com.crowlines.stratum;

import org.junit.Assert;
import org.junit.Test;

import com.crowlines.stratum.server.StratumTestFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJob {

    @Test
    public void testJob() throws JsonProcessingException {
        Job job = StratumTestFactory.createJob();
        ObjectMapper mapper = new ObjectMapper();
        
        String json = mapper.writeValueAsString(job);
        
        System.out.println(json);
        
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

}
