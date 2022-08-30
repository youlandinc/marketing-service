package com.youland.markting.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppTests {

    boolean enable() { return false; }

    @Test
    @EnabledIf("enable")
    public void contextLoads() {

    }

    @Test
    @EnabledIf("enable")
    void test() throws Exception {

    }
}
