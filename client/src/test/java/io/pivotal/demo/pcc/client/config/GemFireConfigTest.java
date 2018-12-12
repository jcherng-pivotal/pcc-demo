package io.pivotal.demo.pcc.client.config;

import org.apache.geode.cache.GemFireCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GemFireConfig.class})
@DirtiesContext
public class GemFireConfigTest {
    @Autowired
    private GemFireCache cache;

    @Test
    public void testGemFireCache() {
        assertThat(cache, notNullValue());
    }
}