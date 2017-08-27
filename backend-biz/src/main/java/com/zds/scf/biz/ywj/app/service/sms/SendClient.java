package com.zds.scf.biz.ywj.app.service.sms;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

@Component
public class SendClient {
    HttpClient client = new HttpClient();
    @Value("${home.sms.appKey}")
    private  String appKey;

    @Value("${home.sms.appSecret}")
    private  String appSecret;

    @Value("${home.sms.verifyCode.templateID}")
    private  String templateid;

  /*  String appKey = "5a3cfd3341e94cf62a082b63dd745316";
    String appSecret = "318f12598b3f";*/


    public String sendSmsCode(String mobile){
        String nonce =  String.valueOf(getRandNum(1,999999));
        String curTime = String.valueOf(System.currentTimeMillis());
        String checkSum = CheckSumBuilder.getCheckSum(appSecret,nonce,curTime);

        String response = "";
        PostMethod postMethod=new PostMethod("https://api.netease.im/sms/sendcode.action");
        postMethod.addParameter("mobile",mobile);
        postMethod.addParameter("templateid",templateid);
        postMethod.addParameter("codeLen",String.valueOf(6));
        postMethod.addRequestHeader("AppKey",appKey);
        postMethod.addRequestHeader("Nonce",nonce);
        postMethod.addRequestHeader("CurTime",curTime);
        postMethod.addRequestHeader("CheckSum",checkSum);
        postMethod.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        try {
            client.getHttpConnectionManager().getParams()
                    .setConnectionTimeout(50000);// 设置连接时间

            int status = client.executeMethod(postMethod);
           // if (status == HttpStatus.SC_OK) {
                InputStream inputStream = postMethod.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }
                response = stringBuffer.toString();
           // } else {
           //     response = "fail";
          // }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }
        return response;
    }

    public String sendSmsInfo(List mobiles, String templateid, List params){
        String nonce =  String.valueOf(getRandNum(1,999999));
        String curTime = String.valueOf(System.currentTimeMillis());
        String checkSum = CheckSumBuilder.getCheckSum(appSecret,nonce,curTime);

        String response = "";
        PostMethod postMethod=new PostMethod("https://api.netease.im/sms/sendtemplate.action");
        postMethod.addParameter("mobiles",new JSONArray(mobiles).toString());
        postMethod.addParameter("templateid",templateid);

        postMethod.addParameter("params",new JSONArray(params).toString());
        postMethod.addRequestHeader("AppKey",appKey);
        postMethod.addRequestHeader("Nonce",nonce);
        postMethod.addRequestHeader("CurTime",curTime);
        postMethod.addRequestHeader("CheckSum",checkSum);
        postMethod.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        try {
            client.getHttpConnectionManager().getParams()
                    .setConnectionTimeout(50000);// 设置连接时间

            int status = client.executeMethod(postMethod);
            // if (status == HttpStatus.SC_OK) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
            response = stringBuffer.toString();
            // } else {
            //     response = "fail";
            // }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }
        return response;
    }

    private int getRandNum(int min, int max) {
        int randNum = min + (int)(Math.random() * ((max - min) + 1));
        return randNum;
    }



}
