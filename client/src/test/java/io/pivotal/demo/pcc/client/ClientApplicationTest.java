package io.pivotal.demo.pcc.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ClientApplication.class})
public class ClientApplicationTest {
    @Autowired
    private ApplicationContext context;

    @Test
    public void testApplicationContext() {
        assertThat(context, notNullValue());
    }
}