package com.youland.marketing.test;

import com.youland.marketing.service.impl.MarketingEmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppTests {
    @Autowired
    private MarketingEmailService marketingEmailService;

    boolean enable() { return true; }

    @Test
    @EnabledIf("enable")
    void contextLoads() {

    }

    @Test
    @EnabledIf("enable")
    void test_sendEmail() {
        Assertions.assertDoesNotThrow(() -> marketingEmailService.sendEmail());
    }
}
