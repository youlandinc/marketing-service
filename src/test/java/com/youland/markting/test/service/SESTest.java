package com.youland.markting.test.service;

import com.youland.markting.service.impl.MarketingUserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Destination;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author chenning
 */
public class SESTest {
    private static SesClient client ;
    boolean enable() { return false; }

    @BeforeAll
    public static void setUp() {
        client = SesClient.builder()
                .region(Region.US_WEST_1)
                .build();
    }

    @Test
    @EnabledIf("enable")
    public void SendMessage() {
        String sender="marketing@youland.com";
        String subject="aws ses test";
        String bodyHTML = "hello every body happy";
        Destination destination = Destination.builder()
                .toAddresses("ning@youland.com")
                .ccAddresses("531237716@qq.com")
                .build();
        assertDoesNotThrow(() -> MarketingUserService.send(client, sender, destination, subject, bodyHTML));
        System.out.println("Test passed");
    }
}
