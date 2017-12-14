package com.utils;

import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/14.
 */
public class CommonUtil {
    /**
     * 判断cron时间表达式正确性
     * @param cronExpression
     * @return
     */
    public static boolean isValidExpression(final String cronExpression){
        CronTriggerImpl trigger = new CronTriggerImpl();
        try {
            trigger.setCronExpression(cronExpression);
            Date date = trigger.computeFirstFireTime(null);
            return date != null && date.after(new Date());
        } catch (ParseException e) {
        }
        return false;
    }
}
