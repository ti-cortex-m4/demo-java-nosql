package demo.async.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import demo.async.AsyncIntegrationTest;
import demo.async.AsyncIntegrationTestConfig;
import com.couchbase.client.java.Bucket;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AsyncIntegrationTestConfig.class })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class })
public class ClusterServiceLiveTest extends AsyncIntegrationTest {

    @Autowired
    private ClusterService couchbaseService;

    private Bucket defaultBucket;

    @Test
    public void whenOpenBucket_thenBucketIsNotNull() throws Exception {
        defaultBucket = couchbaseService.openBucket("default", "");
        assertNotNull(defaultBucket);
        assertFalse(defaultBucket.isClosed());
        defaultBucket.close();
    }
}
