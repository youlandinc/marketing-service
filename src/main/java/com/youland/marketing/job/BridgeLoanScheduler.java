package com.youland.marketing.job;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ScheduledFuture;


@Slf4j
@Component
@RequiredArgsConstructor
public class BridgeLoanScheduler {

    private final Logger logger = LoggerFactory.getLogger(BridgeLoanScheduler.class);

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;


    /**
     * start task
     * @return
     */
    @PostConstruct
    public boolean startCorn() {

        if (Objects.nonNull(future)) {
            future.cancel(true);
            logger.info("task has been closed. ");
        }
        String cornConfig = "0 0/60 1-3 * * ? ";
        future = threadPoolTaskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                logger.info("Start encompass system job expression:: " + sdf.format(LocalDateTime.now()));

                logger.info("End encompass system job expression:: " + sdf.format(LocalDateTime.now()));
            }
        }, new CronTrigger(cornConfig));

        logger.info("task has started.");
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


        threadPoolTaskScheduler.execute(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                logger.info("Start encompass manual job expression:: " + sdf.format(LocalDateTime.now()));


                logger.info("End encompass manual job expression:: " + sdf.format(LocalDateTime.now()));
            }
        });
        return true;
    }

}

