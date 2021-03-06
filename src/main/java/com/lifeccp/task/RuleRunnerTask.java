package com.lifeccp.task;

import com.lifeccp.service.RuleRunnerService;
import com.lifeccp.util.CodeGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Star on 2015/9/15.
 */
@Component
@Configurable
@EnableScheduling
public class RuleRunnerTask {

    private Logger logger = Logger.getLogger(this.getClass());
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);

    @Autowired
    private RuleRunnerService ruleRunnerService;

    /*@Scheduled(fixedRate = 1000 * 30)
    public void reportCurrentTime(){
        System.out.println("Scheduling Tasks Examples: The time is now " + simpleDateFormat().format(new Date()));
    }*/

    //每1分钟执行一次
    @Scheduled(cron = "* */15 * * * *")
    public void ruleRunner() {
        String runnerTag = simpleDateFormat.format(System.currentTimeMillis());
        String batchNo = CodeGenerator.ShortText(runnerTag)[0];
        logger.info("rule runner[" + batchNo + "] ---> begin at " + runnerTag);
        try {
            ruleRunnerService.execute(batchNo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("rule runner[" + batchNo + "] ---> end at " + simpleDateFormat.format(System.currentTimeMillis()));
    }
}
