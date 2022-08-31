package com.youland.marketing.service.impl;

import cn.hutool.core.lang.Dict;
import com.youland.marketing.dao.entity.EmailUser;
import com.youland.marketing.dao.repository.EmailUserRepository;
import com.youland.marketing.model.EmailSender;
import com.youland.marketing.service.IMarketingEmailService;
import com.youland.marketing.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chenning
 */
@Service
@RequiredArgsConstructor
public class MarketingEmailService implements IMarketingEmailService {
    private final EmailUserRepository emailUserRepository;

    public static List<EmailSender> senderList = Arrays.asList(
            new EmailSender("7a3b60c5-f3e7-4a76-8e28-e447c3417963", "Jason Xue", "jason@youland.com", "10-439-2415"),
            new EmailSender("2d8b6ab6-b2ea-4e30-bfd1-f0453eeaf88d","Leslie Li", "leslie@youland.com", "510-439-2417"),
            new EmailSender("f3825b68-d95c-4e09-bdfb-6e1fceea640a", "Ruyi Li", "ruyi@youland.com", "415-813-7798"),
            new EmailSender("980783d2-c88f-4359-88a4-9a11b7870a20", "Angie Fang", "angie@youland.com","1-833-968-5263"),
            new EmailSender("06333511-45f1-4f29-9a9c-e588ceb4476c", "Richard Jia", "richard@youland.com","510-759-8888"),
            new EmailSender("bfc8af0c-25be-48f1-8c1b-2b6a6ea60c26", "Zoey Yang", "zoey@Youland.com","1-833-968-5263"),
            new EmailSender("c2dabc26-fb37-4e6b-bc07-242a809ab504", "Dana Lv", "dana@Youland.com", "1-833-968-5263"),
            new EmailSender("08f870fe-c45b-42cf-81a2-9afbd46f047c", "Dorothy Feng", "dorothy@Youland.com", "1-833-968-5263")
    );

    public void sendEmail() {
        AtomicLong atomicLong = new AtomicLong(0L);
        EmailUser emailUser = emailUserRepository.getReferenceById(atomicLong.get());
        if(!StringUtils.hasText(emailUser.getEmail())) {
            emailUser = emailUserRepository.getReferenceById(atomicLong.addAndGet(1));
        }
        String templateName = "中文".equals(emailUser.getTemplate())?"index_cn.html":"index.html";
        EmailSender sender = senderList.get(1);
        Dict context = Dict.create()
                .set("phone", sender.tel())
                .set("email", sender.email());

        EmailUtil.sendOutlookEmail(sender,"", templateName, context, emailUser.getEmail());
    }
}
