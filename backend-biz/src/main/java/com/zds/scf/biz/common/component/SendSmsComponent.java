package com.zds.scf.biz.common.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cp.boot.core.EnvironmentHolder;
import com.zds.common.lang.exception.Exceptions;
import com.zds.scf.biz.common.dto.SmsRequestDto;
import com.zds.scf.biz.common.dto.SmsResponseDto;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yy at 2017/2/7 16:56
 */
@Component
public class SendSmsComponent {
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 友阿短信发送接口
     *
     * @param phone 手机号
     * @param text  内容
     * @return
     */
    public SmsResponseDto sendSMS(String phone, String text) {
        SmsRequestDto smsRequestDto = new SmsRequestDto();
        smsRequestDto.setPhone(phone);
        smsRequestDto.setText(text);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", EnvironmentHolder.get().getProperty("zds.common.postManUsername"));
        paramMap.put("password", EnvironmentHolder.get().getProperty("zds.common.postManPassword"));
        paramMap.put("channel", Integer.parseInt(EnvironmentHolder.get().getProperty("zds.common.channel")));
        paramMap.put("data", smsRequestDto);
        String jsonParam = JSON.toJSONString(paramMap);
        String result = post(EnvironmentHolder.get().getProperty("zds.common.postManServerUrl"), jsonParam);
        JSONObject jsonObject = JSON.parseObject(result);
        SmsResponseDto smsResponseModel = new SmsResponseDto();
        if (jsonObject.containsKey("returnCode") && Integer.parseInt(jsonObject.getString("returnCode")) == 1) {
            logger.info("短信发送成功,手机号:{},内容:{}", phone, text);
            smsResponseModel.setSuccess(true);
            smsResponseModel.setText(text);
            return smsResponseModel;
        } else {
            logger.info("短信请求失败:{}", phone);
            smsResponseModel.setSuccess(false);
            smsResponseModel.setText(text);
            smsResponseModel.setErrorMsg(jsonObject.getString("info"));
            return smsResponseModel;
        }
    }
    private String post(String url, String params) {
        logger.info("发送短信http请求{},参数:{}", url, params);
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
        client.getHttpConnectionManager().getParams().setSoTimeout(10000);
        PostMethod method = new PostMethod(url);
        method.addParameter("request", params);
        HttpMethodParams param = method.getParams();
        param.setContentCharset("UTF-8");
        StringBuffer result = new StringBuffer();
        String line;
        try {
            int statusCode = client.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                logger.info("短信http请求{}成功,返回状态码:{}", url, statusCode);
                InputStream stream = method.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                while (null != (line = br.readLine())) {
                    result.append(line).append("\n");
                }
            } else{
                throw Exceptions.newRuntimeException("网络错误,返回码:{}"+statusCode);
            }
        } catch (IOException e) {
            logger.error("读取短信请求返回数据错误:{}",e);
            e.printStackTrace();
        }
        method.releaseConnection();
        logger.info("短信http请求结果:{}", result.toString());
        return result.toString();
    }
}
