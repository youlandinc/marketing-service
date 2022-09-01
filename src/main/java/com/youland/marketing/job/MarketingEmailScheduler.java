package com.youland.marketing.job;
import com.youland.marketing.service.IMarketingEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ScheduledFuture;


@Slf4j
@Component
@RequiredArgsConstructor
public class MarketingEmailScheduler {
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final IMarketingEmailService marketingEmailService;
    private ScheduledFuture<?> future;

    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    /**
     * start task
     * @return
     */
    @PostConstruct
    public boolean startCorn() {
        if (Objects.nonNull(future)) {
            future.cancel(true);
            log.info("task has been closed. ");
        }
        String cornConfig = "0/30 * 7,8,9,10 * 1,9 ?";
        future = threadPoolTaskScheduler.schedule(() -> {
            log.info("Start send marketing email system job expression:: " + df.format(LocalDateTime.now()));
            marketingEmailService.sendEmail();
            log.info("End send marketing email system job expression:: " + df.format(LocalDateTime.now()));
        }, new CronTrigger(cornConfig));

        log.info("task has started.");
        return false;
    }

    public boolean stopCorn() {
        if (future != null) {
            future.cancel(true);
            log.info("task has been closed.");
        }
        return false;
    }

    /**
     * Manual task
     * @return
     */
    public boolean manualTask(final Date startDate, final Date endDate){
        threadPoolTaskScheduler.execute(() -> {
            log.info("Start encompass manual job expression:: " + df.format(LocalDateTime.now()));
            marketingEmailService.sendEmail();
            log.info("End encompass manual job expression:: " + df.format(LocalDateTime.now()));
        });
        return true;
    }

}

