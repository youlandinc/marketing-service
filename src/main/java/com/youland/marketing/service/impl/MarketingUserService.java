package com.youland.marketing.service.impl;

import com.youland.marketing.dao.entity.MarketingUser;
import com.youland.marketing.dao.repository.MarketingUserRepository;
import com.youland.marketing.service.IMarketingUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import java.util.List;

/**
 * @author chenning
 */
@Service
@RequiredArgsConstructor
public class MarketingUserService implements IMarketingUserService {
    private final MarketingUserRepository marketingUserRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MarketingUser addMarketingUser(MarketingUser marketingUser) {
        sendEmail(marketingUser);
        return marketingUserRepository.save(marketingUser);
    }

    @Override
    public List<MarketingUser> getMarketingUserList(){
        return marketingUserRepository.findAll();
    }

    private void sendEmail(MarketingUser user) {
        try (SesClient client = SesClient.builder()
                .region(Region.US_WEST_1)
                .build()){

            String emailSubject = "A new lead from %s";
            String emailBody = "<table border='1' cellpadding='20px'><tr><th>user name</th><th>user phone</th><th>user email</th></tr>" +
                    "<tr><td>%s</td><td>%s</td><td>%s</td></tr></table>";

            String[] targetEmail = {"leslie@youland.com", "jason@youland.com", "angie@youland.com",
                    "richard@youland.com", "zoey@youland.com"};

            Destination destination = Destination.builder()
                    .toAddresses("ruyi@youland.com")
                    .ccAddresses(targetEmail)
                    .build();

            send(client,"marketing@youland.com", destination,
                    String.format(emailSubject, user.getName()),
                    String.format(emailBody, user.getName(), user.getPhone(), user.getEmail()));
        }

    }

    public static void send(SesClient client, String sender, Destination destination, String subject, String bodyHTML) {
        Content content = Content.builder()
                .data(bodyHTML)
                .build();

        Content sub = Content.builder()
                .data(subject)
                .build();

        Body body = Body.builder()
                .html(content)
                .build();

        Message msg = Message.builder()
                .subject(sub)
                .body(body)
                .build();

        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(destination)
                .message(msg)
                .source(sender)
                .build();

        client.sendEmail(emailRequest);
    }

}
