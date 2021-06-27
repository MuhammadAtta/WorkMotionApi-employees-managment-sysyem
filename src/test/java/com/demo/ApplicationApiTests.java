package com.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Muhammad Atta
 *
 */

@ContextConfiguration({"classpath*:spring/applicationContext.xml"})
@SpringBootConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/test.properties")
public class ApplicationApiTests {
    @Test
    public void contextLoads() {
    }
}
