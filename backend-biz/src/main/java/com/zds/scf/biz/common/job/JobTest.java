package com.zds.scf.biz.common.job;

import com.cp.boot.job.BaseJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yy at 2017/2/9 13:50
 *   // name  定时任务名称 建议写成非中文 例如testJob001,非空
 *   //caption 定时任务描述 ,可为空
 *   // cycleType  定时任务期间类型  S,D,W,M,Y    非空
 *  //执行时间 time  ，非空
    yyyy-MM-dd HH:mm:ss 指定执行日期 例：2012-04-17 13:24:30 非期间型指定日期时间
    Y型 MM-dd HH:mm:ss 年期间 例：04-17 13:24:30
    M型 dd HH:mm:ss 月期间 例：17 13:24:30
    W型 t HH:mm:ss 周期间 例：3 13:24:30 每周二的13:24:30     1为周日
    D型 HH:mm:ss 天期间  例：13:24:30
    S型 ss 秒期间 例：360 每360秒执行一次
 */
//@QuartzJob(name = "test01",caption = "定时任务1",cycleType = "D",time = "14:17:00")
public class JobTest extends BaseJob{
        private static final Logger logger = LoggerFactory.getLogger(JobTest.class);
    @Override
    public void work() throws Exception {
         logger.info("测试定时任务");
    }
}
