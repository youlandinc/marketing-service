package com.youland.marketing.job;
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
        String cornConfig = "0 0/60 1-3 * * ? ";
        future = threadPoolTaskScheduler.schedule(() -> {
            log.info("Start encompass system job expression:: " + df.format(LocalDateTime.now()));

            log.info("End encompass system job expression:: " + df.format(LocalDateTime.now()));
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


            log.info("End encompass manual job expression:: " + df.format(LocalDateTime.now()));
        });
        return true;
    }

}

