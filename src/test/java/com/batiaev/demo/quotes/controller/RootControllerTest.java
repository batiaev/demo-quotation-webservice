package com.batiaev.demo.quotes.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * RootControllerTest
 *
 * @author anton
 * @since 13/07/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RootControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private RootController controller;

    @Test
    public void contextLoads() throws Exception {
    }

    @Test
    public void testAutowire() throws Exception {
        assertNotNull(controller);
    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        String response = restTemplate.getForObject("http://localhost:" + port + "/quotes/currencies/rub", String.class);
        assertTrue(response.contains("Рубль"));
    }
}