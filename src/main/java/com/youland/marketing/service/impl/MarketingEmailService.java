package com.youland.marketing.service.impl;

import cn.hutool.core.lang.Dict;
import com.microsoft.graph.http.GraphServiceException;
import com.youland.marketing.dao.entity.EmailUser;
import com.youland.marketing.dao.entity.ErrorEmailUser;
import com.youland.marketing.dao.repository.EmailUserRepository;
import com.youland.marketing.dao.repository.ErrorEmailUserRepository;
import com.youland.marketing.model.EmailSender;
import com.youland.marketing.service.IMarketingEmailService;
import com.youland.marketing.util.EmailUtil;
import com.youland.marketing.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
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
    private final ErrorEmailUserRepository errorEmailUserRepository;

    private static final String TO_ID_KEY = "email_user_id";
    private static final String TOTAL_SENT_KEY = "total_sent_count";
    private static final String CURRENT_SENT_KEY = "_sent_count";
    private static final String EMAIL_MATCHER = "^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";

    public static List<EmailSender> senderList = Arrays.asList(
            // new EmailSender("7a3b60c5-f3e7-4a76-8e28-e447c3417963", "Jason Xue", "jason@youland.com", "10-439-2415"),
            new EmailSender("2d8b6ab6-b2ea-4e30-bfd1-f0453eeaf88d", "Leslie Li", "leslie@youland.com", "510-439-2417"),
            // new EmailSender("f3825b68-d95c-4e09-bdfb-6e1fceea640a", "Ruyi Li", "ruyi@youland.com", "415-813-7798"),
            new EmailSender("980783d2-c88f-4359-88a4-9a11b7870a20", "Angie Fang", "angie@youland.com", "1-833-968-5263"),
            new EmailSender("06333511-45f1-4f29-9a9c-e588ceb4476c", "Richard Jia", "richard@youland.com", "510-759-8888"),
            new EmailSender("bfc8af0c-25be-48f1-8c1b-2b6a6ea60c26", "Zoey Yang", "zoey@Youland.com", "1-833-968-5263"),
            new EmailSender("c2dabc26-fb37-4e6b-bc07-242a809ab504", "Dana Lv", "dana@Youland.com", "1-833-968-5263"),
            new EmailSender("08f870fe-c45b-42cf-81a2-9afbd46f047c", "Dorothy Feng", "dorothy@Youland.com", "1-833-968-5263")
    );

    public static List<Map.Entry<String,String>> titleList = Arrays.asList(
            Map.entry("???????????????????????????????????????????????????","Holiday Special Rate - Call YouLand"),
            Map.entry("?????????????????????????????????????????????","YouLand Season???s Greetings and Holiday Savings!"),
            Map.entry("?????????????????????????????????????????????????????????","New Year, New Home, Start with YouLand Bridge Loan")
    );

    @Override
    public boolean sendEmail() {
        if (!StringUtils.hasText(redisTemplate.opsForValue().get(TO_ID_KEY))) {
            // ?????????
            redisTemplate.opsForValue().set(TO_ID_KEY, "0");
            redisTemplate.opsForValue().set(TOTAL_SENT_KEY, "0");
        }
        String dateLimitKey = LocalDate.now() + CURRENT_SENT_KEY;
        if (!StringUtils.hasText(redisTemplate.opsForValue().get(dateLimitKey))) {
            redisTemplate.opsForValue().set(dateLimitKey, "0", Duration.ofHours(24));
        }

        final long limitNum = 500;
        //??????????????????????????????????????? ??????????????????
        Long todaySendNum = redisTemplate.opsForValue().increment(dateLimitKey,0);
        log.info("dateLimitKey:{}.", todaySendNum);
        if (todaySendNum != null && todaySendNum > limitNum) {
            return false;
        }

        List<EmailUser> emailUsers = getEightEmailUsers();
        int j = emailUsers.size();
        if (j > 0) {
            for (int i = 0; i < j; i++) {
                EmailUser user = emailUsers.get(i);
                EmailSender sender = senderList.get((i));
                log.info("??????????????????????????????: {}, ?????????: {}", sender.email(), JsonUtil.stringify(user));

                boolean isZh = StringUtils.hasText(user.getTemplate()) && user.getTemplate().contains("??????");
                String templateName = isZh ? "index_chris_cn.html" : "index_chris.html";
                // get email title
                Map.Entry<String, String> title = titleList.get((i+1)%3);
                String subject = isZh ? title.getKey() : title.getValue();
                Dict context = Dict.create()
                        .set("name", user.getName().trim())
                        .set("phone", sender.tel())
                        .set("email", sender.email());

                try {
                    Thread.sleep(2*1000L);
                    EmailUtil.sendOutlookEmail(sender, subject, templateName, context, user.getEmail().trim());
                    log.info("??????????????????????????????{}, ?????????????????????{}", redisTemplate.opsForValue().increment(dateLimitKey),
                            redisTemplate.opsForValue().increment(TOTAL_SENT_KEY));
                    user.setErrorInfo("Success");
                    emailUserRepository.save(user);
                } catch (GraphServiceException e) {
                    user.setErrorInfo(e.getServiceError().code);
                    emailUserRepository.save(user);
                    log.error("????????????????????????: ", e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private List<EmailUser> getEightEmailUsers() {
        List<EmailUser> list = new ArrayList<>();
        boolean hasData = true;

        do {
            Long idNum = redisTemplate.opsForValue().increment(TO_ID_KEY, 0);
            Optional<EmailUser> optional = emailUserRepository.findById(idNum+1);
            log.info("idNum :{}, emailUser :{}.", idNum, JsonUtil.toJsonNode(optional.get()));
            if (optional.isPresent()) {

                EmailUser emailUser = optional.get();
                if (!Boolean.TRUE.equals(emailUser.getUnsubscribe())
                        && StringUtils.hasText(emailUser.getEmail())
                        && Pattern.matches(EMAIL_MATCHER, emailUser.getEmail().trim().toLowerCase())) {
                    list.add(emailUser);
                    log.info("????????????????????????:{}????????????????????????", JsonUtil.stringify(emailUser));
                }else {
                    log.info("???????????????????????????:{}, ????????????", JsonUtil.stringify(emailUser));
                }
                Long newIdNum = redisTemplate.opsForValue().increment(TO_ID_KEY);
                log.info("newIdNum:{}.",newIdNum);
            } else {
                // ???idNum?????????????????????????????????????????????
                Long newIdNum = redisTemplate.opsForValue().increment(TO_ID_KEY);
                log.info("newIdNum:{}.",newIdNum);
                hasData = false;
            }
        } while (hasData && list.size() < 6);

        return list;
    }

    @Override
    public boolean unSubscribe(String email) {
        EmailUser emailUser = emailUserRepository.findFirstByEmailIgnoreCase(email.toUpperCase());
        if (emailUser != null) {
            emailUser.setUnsubscribe(true);
            emailUserRepository.save(emailUser);
        }
        return true;
    }

    @Override
    public boolean findErrorEmail() {

        var list = emailUserRepository.findAll();

        list.forEach(emailUser -> {
            if (!Boolean.TRUE.equals(emailUser.getUnsubscribe())
                    && StringUtils.hasText(emailUser.getEmail())
                    && Pattern.matches(EMAIL_MATCHER, emailUser.getEmail().trim().toLowerCase())) {

            }else {
                ErrorEmailUser errorEmailUser = new ErrorEmailUser();
                BeanUtils.copyProperties(emailUser, errorEmailUser);
                errorEmailUserRepository.save(errorEmailUser);
            }
        });

        return true;
    }
}
