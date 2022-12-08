package com.youland.marketing.test.service;

import cn.hutool.core.lang.Dict;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.google.common.collect.Lists;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.GraphServiceClient;
import com.youland.marketing.dao.entity.EmailUser;
import com.youland.marketing.model.EmailSender;
import com.youland.marketing.util.EmailUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class OutlookTest {

    boolean enable() { return false; }

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


        var userCollectionPage = graphClient.users().buildRequest().get();
        var users = userCollectionPage.getCurrentPage();

        AtomicReference<User> userRico = new AtomicReference<>();
        users.stream().forEach(user -> {
            if(Objects.equals("rico@Youland.com", user.mail)){
                userRico.set(user);
            }
        });

        var user = userRico.get();

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

    @Test
    @EnabledIf("enable")
    void send_template_email() {
        EmailSender sender = new EmailSender("0553cb68-e618-453c-b572-c77d7cc7c762", "Rico Shen",
                "rico@youland.com", "1-833-968-5263");
        EmailUser emailUser = new EmailUser();
        emailUser.setName("mace");
        emailUser.setEmail("mace@youland.com");
        emailUser.setTemplate("英文");

        boolean isCN = "中文".equals(emailUser.getTemplate());
        String templateName = isCN ? "index_chris_cn.html" : "index_chris.html";
        String subject = isCN ? "DSCR类项目最低利率，立刻查看！" : "Amazing Rate with YouLand DSCR Loan！";
        Dict context = Dict.create()
                .set("name", emailUser.getName())
                .set("phone", sender.tel())
                .set("email", sender.email());

        EmailUtil.sendOutlookEmail(sender, subject, templateName, context, emailUser.getEmail());
    }

    @Test
    void verify_email_test() {
        String emailMatcher="^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
        String email = " elias.palacios@compass.com ";
        boolean isMatches = Pattern.matches(emailMatcher, email.trim().toLowerCase());
        System.out.println(isMatches);
    }

}
