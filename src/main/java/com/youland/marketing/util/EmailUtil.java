package com.youland.marketing.util;

import cn.hutool.core.lang.Dict;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.google.common.collect.Lists;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.GraphServiceClient;
import com.youland.marketing.model.EmailSender;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.List;

/**
 * @author chenning
 */
@Slf4j
public final class EmailUtil {
    private EmailUtil(){}
    private final static GraphServiceClient graphClient;

    static {
        final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId("39f214d1-35e4-44ad-8830-7fd279332558")
                .clientSecret("62N8Q~NFLKH6VzPYoBxwObubDqwoOENGaEFaXcYG")
                .tenantId("38288c8c-4f6e-46a6-9022-616842675b45")
                .build();

        List<String> scopes = Lists.newArrayList("https://graph.microsoft.com/.default");
        final TokenCredentialAuthProvider tokenCredAuthProvider =
                new TokenCredentialAuthProvider(scopes, clientSecretCredential);

        graphClient = GraphServiceClient
                .builder()
                .authenticationProvider(tokenCredAuthProvider)
                .buildClient();
    }

    public static void sendOutlookEmail(EmailSender sender, String subject, String templateName, Dict context, String... to) {
        TemplateEngine engine = TemplateUtil.createEngine(
                new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH)
        );
        Template template = engine.getTemplate(templateName);
        String content = template.render(context);
        log.info("发送者:{}, 主题:{}, 模板名:{}, 接收者:{}. 正在发送...", sender.email(), subject, templateName, to);
        sendOutlookEmail(sender.email(), to, null, subject, content);
    }

    public static void sendOutlookEmail(String sender, String[] to, String[] cc, String subject, String content) {
        Message message = new Message();
        message.subject = subject;

        ItemBody body = new ItemBody();
        body.contentType = BodyType.HTML;
        body.content = content;
        message.body = body;

        message.toRecipients = getRecipients(to);

        if(cc!=null && cc.length>0) {
            message.ccRecipients = getRecipients(cc);
        }

        graphClient.users(sender)
                .sendMail(UserSendMailParameterSet
                        .newBuilder()
                        .withMessage(message)
                        .withSaveToSentItems(true)
                        .build())
                .buildRequest()
                .post();
    }

    private static LinkedList<Recipient> getRecipients(String[] email) {
        LinkedList<Recipient> recipientsList = new LinkedList<>();
        for(String str: email){
            Recipient recipient = new Recipient();
            EmailAddress emailAddress = new EmailAddress();
            emailAddress.address = str;
            recipient.emailAddress = emailAddress;
            recipientsList.add(recipient);
        }
        return recipientsList;
    }
}
