package com.youland.markting.test;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.google.common.collect.Lists;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.GraphServiceClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class AppTests {

    boolean enable() { return true; }

    @Test
    @EnabledIf("enable")
    public void contextLoads() {

    }

    @Test
    @EnabledIf("enable")
    void sendOutlookEmail() throws Exception {

        final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId("39f214d1-35e4-44ad-8830-7fd279332558")
                .clientSecret("62N8Q~NFLKH6VzPYoBxwObubDqwoOENGaEFaXcYG")
                .tenantId("38288c8c-4f6e-46a6-9022-616842675b45")
                .build();

        List<String> scopes = Lists.newArrayList("https://graph.microsoft.com/.default");
        final TokenCredentialAuthProvider tokenCredAuthProvider =
                new TokenCredentialAuthProvider(scopes, clientSecretCredential);

        final GraphServiceClient graphClient = GraphServiceClient
                .builder()
                .authenticationProvider(tokenCredAuthProvider)
                .buildClient();

        System.out.println("GraphServiceClient: "+graphClient);


        //GraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider( authProvider ).buildClient();

        Message message = new Message();
        message.subject = "Meet for lunch?";
        ItemBody body = new ItemBody();
        body.contentType = BodyType.TEXT;
        body.content = "The new cafeteria is open.";
        message.body = body;
        LinkedList<Recipient> toRecipientsList = new LinkedList<Recipient>();
        Recipient toRecipients = new Recipient();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.address = "mace@youland.com";
        toRecipients.emailAddress = emailAddress;
        toRecipientsList.add(toRecipients);
        message.toRecipients = toRecipientsList;
        LinkedList<Recipient> ccRecipientsList = new LinkedList<Recipient>();
        Recipient ccRecipients = new Recipient();
        EmailAddress emailAddress1 = new EmailAddress();
        emailAddress1.address = "rico@youland.com";
        ccRecipients.emailAddress = emailAddress1;
        ccRecipientsList.add(ccRecipients);
        message.ccRecipients = ccRecipientsList;

        boolean saveToSentItems = true;

        graphClient.users().buildRequest().get();
        // Rico: 0553cb68-e618-453c-b572-c77d7cc7c762
        // Ning: 9de72f85-d7f7-478f-b88d-0f423d4b248a
        graphClient.users("9de72f85-d7f7-478f-b88d-0f423d4b248a")
                .sendMail(UserSendMailParameterSet
                        .newBuilder()
                        .withMessage(message)
                        .withSaveToSentItems(saveToSentItems)
                        .build())
                .buildRequest()
                .post();
    }
}
