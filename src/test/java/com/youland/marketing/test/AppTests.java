package com.youland.marketing.test;

import com.youland.marketing.dao.repository.EmailUserRepository;
import com.youland.marketing.service.impl.MarketingEmailService;
import com.youland.marketing.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppTests {
    @Autowired
    private MarketingEmailService marketingEmailService;
    @Autowired
    private EmailUserRepository emailUserRepository;

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

    @Test
    @EnabledIf("enable")
    void errorEmail(){
        marketingEmailService.findErrorEmail();
    }

    @Test
    @EnabledIf("enable")
    void findEmailIgnoreCase(){
        Assertions.assertNotNull(emailUserRepository.findFirstByEmailIgnoreCase("alex@xpfunding.com".toUpperCase()));

    }
}
