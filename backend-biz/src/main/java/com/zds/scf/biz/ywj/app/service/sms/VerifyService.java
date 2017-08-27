package com.zds.scf.biz.ywj.app.service.sms;

import com.alibaba.fastjson.JSONObject;
import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.dto.SMSVerifyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/7/15.
 */

@Component
public class VerifyService {

    static final int effectiveTime = 60*10;//短信有效时间

    static final int intervalTime = 60;//发短信间隔时间

    static final int maxTimes = 30;//一个手机或ip一天最多能发的验证次数

    private Timer timer = new Timer();

    @PostConstruct
    public void initMethod() throws Exception {
        Date date = new Date();
        date.setDate(date.getDate()+1);
        date.setHours(3);
        date.setMinutes(0);
        date.setSeconds(0);
        timer.schedule(new ResetTask(),new Date(),60*60*24*1000);
    }


    @Autowired
    SendClient sendClient;

    private Map<String,SMSHolder> verifyCodeHolderMap = new ConcurrentHashMap();

    private Map<String,Counter> phoneProtectMap = new ConcurrentHashMap();

    private Map<String,Counter> ipProtectMap = new ConcurrentHashMap();

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public SMSVerifyDto getVerifyCode(String phone,String ip) {
        doProtect(phone,ip);
        SMSVerifyDto dto = new SMSVerifyDto(phone);
        dto.setPhone(phone);
        String smsString = sendClient.sendSmsCode(phone);
        JSONObject json =  JSONObject.parseObject(smsString);
        if((Integer)json.get("code")!=200){
            CPBusinessException.throwIt(String.valueOf(json.get("msg")));
        }
        String correctVerify =  String.valueOf(json.get("obj"));
        //dto.setVerifyCode(correctVerify);
        verifyCodeHolderMap.put(phone,new SMSHolder(correctVerify));
        dto.setEffective(effectiveTime);

        logger.info("验证码发送成功,手机号:{}", phone);
        return  dto;
    }

    public void sendNoticeMsg(List<String> phones, String templateid, List<String> params) {
        String smsString = sendClient.sendSmsInfo(phones,templateid,params);
        JSONObject json =  JSONObject.parseObject(smsString);
        if((Integer)json.get("code")!=200){
            CPBusinessException.throwIt(String.valueOf(json.get("msg")));
        }

        logger.info("消息发送成功,手机号:{}", phones);
    }

    private void doProtect(String phone,String ip) {
        Counter phoneCounter = phoneProtectMap.get(phone);
        if(phoneCounter != null){
            if(System.currentTimeMillis()-phoneCounter.getLastTime()<1000*intervalTime){
                CPBusinessException.throwIt("同一手机在"+intervalTime+"秒内只允许一次短信请求");
            }
            if(phoneCounter.getTotal()>maxTimes){
                CPBusinessException.throwIt("同一手机在一天内只短信请求不能超过"+maxTimes+"次");
            }
            phoneCounter.count();
        }else{
            phoneProtectMap.put(phone,new Counter());
        }
        Counter ipCounter = ipProtectMap.get(ip);
        if(ipCounter != null){
            if(System.currentTimeMillis()-ipCounter.getLastTime()<1000*intervalTime){
                CPBusinessException.throwIt("同一IP在"+intervalTime+"秒内只允许一次短信请求");
            }
            if(ipCounter.getTotal()>maxTimes){
                CPBusinessException.throwIt("同一IP在一天内只短信请求不能超过"+maxTimes+"次");
            }
            ipCounter.count();
        }else{
            ipProtectMap.put(ip,new Counter());
        }
    }

    public void verifyCode(String phone, String verifyCode) {
        SMSHolder smsHolder =  verifyCodeHolderMap.get(phone);
        if(smsHolder == null){
            CPBusinessException.throwIt("请先获取手机验证码!");
        }
        if(!smsHolder.getCode().equals(verifyCode)){
            CPBusinessException.throwIt(" 验证码输入不正确!");
        }
        if( System.currentTimeMillis()-smsHolder.getTime()>effectiveTime*1000){
            CPBusinessException.throwIt("验证码已过期，请重新获取!");
        }
    }

    private void clear(){
        logger.info("每天3点清空验证码缓存");
        verifyCodeHolderMap.clear();
        phoneProtectMap.clear();
        ipProtectMap.clear();
    }

    private class ResetTask extends TimerTask {
        public void run(){
            clear();
        }
    }

    private static class SMSHolder {
        private String code ;
        private long time;

        public SMSHolder(String code){
            this.code = code;
            this.time = System.currentTimeMillis();
        }

        public String getCode() {
            return code;
        }


        public long getTime() {
            return time;
        }


    }

    private static class Counter {
        private int total ;
        private long lastTime;

        public Counter(){
            this.total = 1;
            this.lastTime = System.currentTimeMillis();
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public long getLastTime() {
            return lastTime;
        }

        public void setLastTime(long lastTime) {
            this.lastTime = lastTime;
        }

        public void count() {
            this.lastTime = System.currentTimeMillis();
            this.total++;
        }
    }
}
