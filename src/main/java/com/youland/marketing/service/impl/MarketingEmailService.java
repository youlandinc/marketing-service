package com.youland.marketing.service.impl;

import cn.hutool.core.lang.Dict;
import com.youland.marketing.dao.entity.EmailUser;
import com.youland.marketing.dao.repository.EmailUserRepository;
import com.youland.marketing.model.EmailSender;
import com.youland.marketing.service.IMarketingEmailService;
import com.youland.marketing.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author chenning
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MarketingEmailService implements IMarketingEmailService {
    private final EmailUserRepository emailUserRepository;
    private final StringRedisTemplate redisTemplate;

    private static final String SERIAL_KEY = "serialNumber";
    private static final String EFFECTIVE_KEY = "effectiveNumber";
    private static final String LIMIT_KEY = "limitNumber";

    public static List<EmailSender> senderList = Arrays.asList(
            new EmailSender("7a3b60c5-f3e7-4a76-8e28-e447c3417963", "Jason Xue", "jason@youland.com", "10-439-2415"),
            new EmailSender("2d8b6ab6-b2ea-4e30-bfd1-f0453eeaf88d", "Leslie Li", "leslie@youland.com", "510-439-2417"),
            new EmailSender("f3825b68-d95c-4e09-bdfb-6e1fceea640a", "Ruyi Li", "ruyi@youland.com", "415-813-7798"),
            new EmailSender("980783d2-c88f-4359-88a4-9a11b7870a20", "Angie Fang", "angie@youland.com", "1-833-968-5263"),
            new EmailSender("06333511-45f1-4f29-9a9c-e588ceb4476c", "Richard Jia", "richard@youland.com", "510-759-8888"),
            new EmailSender("bfc8af0c-25be-48f1-8c1b-2b6a6ea60c26", "Zoey Yang", "zoey@Youland.com", "1-833-968-5263"),
            new EmailSender("c2dabc26-fb37-4e6b-bc07-242a809ab504", "Dana Lv", "dana@Youland.com", "1-833-968-5263"),
            new EmailSender("08f870fe-c45b-42cf-81a2-9afbd46f047c", "Dorothy Feng", "dorothy@Youland.com", "1-833-968-5263")
    );

    @Override
    public boolean sendEmail() {
        if (!StringUtils.hasText(redisTemplate.opsForValue().get(SERIAL_KEY))) {
            // 初始化
            redisTemplate.opsForValue().set(SERIAL_KEY, "0", Duration.ofDays(30));
            redisTemplate.opsForValue().set(EFFECTIVE_KEY, "0", Duration.ofDays(30));
        }
        if (!StringUtils.hasText(redisTemplate.opsForValue().get(LIMIT_KEY))) {
            redisTemplate.opsForValue().set(LIMIT_KEY, "0", Duration.ofHours(6));
        }

        final long limitNum = 3000;
        //当天发送邮件数量超过限制值 当天不再发送
        Long todaySendNum = redisTemplate.opsForValue().increment(LIMIT_KEY);
        if(todaySendNum !=null && todaySendNum > limitNum) {
            return false;
        }

        Long serialNumber = redisTemplate.opsForValue().increment(SERIAL_KEY);
        Optional<EmailUser> emailUserOptional = emailUserRepository.findById(serialNumber);

        //如果 数据库查到了 但是邮箱为空或者不合法 或者取消订阅 则继续查询下一个
        String emailMatcher="[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+";
        while (emailUserOptional.isPresent() && (
                !StringUtils.hasText(emailUserOptional.get().getEmail())
                || !Pattern.matches(emailMatcher, emailUserOptional.get().getEmail().trim())
                || Boolean.TRUE.equals(emailUserOptional.get().getUnsubscribe())
        )) {

            serialNumber = redisTemplate.opsForValue().increment(SERIAL_KEY);
            emailUserOptional = emailUserRepository.findById(serialNumber);
        }

        if (emailUserOptional.isPresent()) {
            EmailUser emailUser = emailUserOptional.get();
            log.info("当前有效邮箱用户信息：{}", emailUser);

            Long effectiveNumber = redisTemplate.opsForValue().increment(EFFECTIVE_KEY);
            boolean isCN = "中文".equals(emailUser.getTemplate());
            String templateName = isCN ? "index_cn.html" : "index.html";
            String subject = isCN ? "有联贷款秋季优惠！一定不能错过的最低利率！" : "Lowest Rate Ever - Call YouLand!";
            EmailSender sender = senderList.get((effectiveNumber.intValue() - 1) % 8);
            Dict context = Dict.create()
                    .set("name", emailUser.getName())
                    .set("phone", sender.tel())
                    .set("email", sender.email());

            EmailUtil.sendOutlookEmail(sender, subject, templateName, context, emailUser.getEmail());
            log.info("当前有效发送成功数：{}", redisTemplate.opsForValue().increment(EFFECTIVE_KEY));
            return true;
        } else {
            return false;
        }
    }
}
