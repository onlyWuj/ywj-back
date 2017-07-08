package com.zds.scf.web.common;

import com.zds.common.lang.exception.Exceptions;
import com.zds.scf.web.common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 微信支付配置
 * Created by yy at 2016/11/10 10:35
 */
@SuppressWarnings("ALL")
@ConfigurationProperties("common.wechat")
public class WeChatProperties {

    private  static Logger logger= LoggerFactory.getLogger(WeChatProperties.class) ;

    //API 接口安全key   经过 yiguoshu186 MD5加密得到
    private static String key = "5fd187330d26df96106e4aa0657048d0";

    //微信分配的公众号ID
    private static String appID = "wx885a53e8cf20687e"; //友阿公众号appID

    //微信支付分配的商户号ID
    private static String mchID = "1407075602";
    //账号secret
    private static String secret = "ab707aa54942447ce3b331e89f667993";  //友阿秘钥

    //HTTPS证书的本地路径 要放到安全目录 防止下载
    private static String certLocalPath = "classpath:/secert/apiclient_cert.p12";

    //HTTPS证书密码，默认密码等于商户号MCHID
    private static String certPassword = "1407075602";

    //友阿微信绑定域名地址
    private static  String youaWeChatBoundDomain="http://scm.e9448.com/get-weixin-code.html?";


    //获取openid url
    private static String openIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
            "appid=APPID" +
            "&secret=SECRET&" +
            "code=CODE&grant_type=authorization_code";

    //全局token地址
    private static String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //全局tickt 地址
    private static String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=TOKEN&type=jsapi";

    //获取openid code 地址
    private static String authCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?";

    //1）公众号支付地址
    public static String payApi = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    //2）公众号支付查询地址
    public static String payQueryApi = "https://api.mch.weixin.qq.com/pay/orderquery";

    //3）退款地址
    public static String refundApi = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    //4）退款查询地址
    public static String refundQueryApi = "https://api.mch.weixin.qq.com/pay/refundquery";

    //订单页面发起支付url
    private static String createOrderPayUrl = "/pay-orders.html";
    //订单列表发起支付url
    private static String orderListPayUrl = "/my-order.html";
    //订单详情发起支付url
    private static String orderDetailPayUrl = "order-detail-isok.html";
    //继续支付发起支付url
    private static String continuePayUrl = "/pay-complited.html";
    //结算页面发起支付
    private static String balanceOrderPayUrl = "/wait_getmoney.html";
    //结算未完成页面发起支付
    private static String balanceContinueOrderPayUrl = "/settle_yes.html";
    //结算中页面发起支付
    private static String balanceIngOrderPayUrl = "/record_settlement_settlementing.html";
    //发送微信公众号通知
    private static String sendWxNoticeUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    //客户付款通知模板id
    private static String customerPaidNoticeTemplateId = "U9qHlX7LWofylsMQ2hxtSK9Fkgu10Hts88wAyEqwL5k";

    public static String getBalanceIngOrderPayUrl() {
        return balanceIngOrderPayUrl;
    }

    public static String getBalanceContinueOrderPayUrl() {
        return balanceContinueOrderPayUrl;
    }

    public static String getKey() {
        return key;
    }

    public static String getAppid() {
        return appID;
    }

    public static String getTokenUrl() {
        return tokenUrl;
    }

    public static String getSendWxNoticeUrl() {
        return sendWxNoticeUrl;
    }

    public static String getCustomerPaidNoticeTemplateId() {
        return customerPaidNoticeTemplateId;
    }

    public static String getSecret() {
        return secret;
    }

    public static String getMchid() {
        return mchID;
    }

    public static String getCertLocalPath() {
        return certLocalPath;
    }

    public static String getCertPassword() {
        return certPassword;
    }

    public static String getTicketUrl() {
        return ticketUrl;
    }

    public static String getAuthCodeUrl() {
        return authCodeUrl;
    }

    public static String getOpenIdUrl() {
        return openIdUrl;
    }

    public static String getOrderListPayUrl() {
        return orderListPayUrl;
    }

    public static String getCreateOrderPayUrl() {
        return createOrderPayUrl;
    }

    public static String getBalanceOrderPayUrl() {
        return balanceOrderPayUrl;
    }

    public static String getContinuePayUrl() {
        return continuePayUrl;
    }

    public static String getOrderDetailPayUrl() {
        return orderDetailPayUrl;
    }

    public static String getPayApi() {
        return payApi;
    }

    public static String getPayQueryApi() {
        return payQueryApi;
    }

    public static String getRefundApi() {
        return refundApi;
    }

    public static String getRefundQueryApi() {
        return refundQueryApi;
    }

    /**
     * 获取 取openid之前需要的code
     *
     * @param servletRequest
     * @return
     */
    public static String getAuthCodeUri(HttpServletRequest request, String userType) throws UnsupportedEncodingException {
        String authCodeUrl = "";
    /*	 authCodeUrl = getAuthCodeUrl();
         authCodeUrl += "appid=" + getAppid();
         authCodeUrl += "&redirect_uri=" + getRedirectUri(serverName,userType);
         authCodeUrl += "&response_type=" + "code";
         authCodeUrl += "&scope=" + "snsapi_base";
         authCodeUrl += "&state="+userType;
         authCodeUrl += "#wechat_redirect";*/
        authCodeUrl =youaWeChatBoundDomain;
        authCodeUrl += "appid=" + getAppid();
        authCodeUrl += "&scope=" + "snsapi_base";
        authCodeUrl += "&state=1";
        String redirectUri = WebUtil.getCurrentPageUrl(request);
        logger.info("微信回调AuthCode地址:{}",redirectUri);
        authCodeUrl += "&redirect_uri=" + URLEncoder.encode(redirectUri, "utf-8");
        return authCodeUrl;
    }

    /**
     * 取openid之前，微信回调商户服务器的url
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unused")
	private static String getRedirectUri(String serverName, String userType) {
        String path = "";
        if (userType.equals("mobile")) {
            path = "/mobile/getOpenId";
        }
        String source = "http://" + serverName + path;
        try {
            return URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw Exceptions.newRuntimeException("转换redirectUri出错");
        }
    }

    /**
     * 获取js config 签名用url
     *
     * @param request
     * @return
     */
    public static String getJsConfigSignatureUrl(String serverName, String payUrl) {
        String path = "/customer" + payUrl;
   /*     String userType= CPContext.getContext().getSeUserInfo().getType();
        if(userType.equals("customer")){
          path = "/customer" + payUrl;
        }else if(userType.equals("sales")){
           path = "/sales" + payUrl;
        }*/
        String url = "http://" + serverName + path;
        return url;
    }

    /**
     * 获取微信后台回调商户服务器url
     *
     * @param request
     * @return
     */
    public static String getNotifyUrl(String serverName) {
        String path = "";
       /* String userType= CPContext.getContext().getSeUserInfo().getType();
        if(userType.equals("customer")){
           path = "/customer/pay/payCallBack";
        }else if(userType.equals("sales")){
           path = "/customer/sales/pay/payCallBack";
        }*/
        String url = "http://" + serverName + path;
        return url;
    }

    /**
     * 获取支付全局token地址
     *
     * @return
     */
    public static String getTokenUri() {
        String tokenUrl = getTokenUrl();
        tokenUrl = tokenUrl.replace("APPID", getAppid());
        tokenUrl = tokenUrl.replace("APPSECRET", getSecret());
        return tokenUrl;
    }

    /**
     * 获取jsconifg签名  需要加密的ticket url
     *
     * @return
     */
    public static String getTicketUrl(String token) {
        String ticketUrl = getTicketUrl();
        ticketUrl = ticketUrl.replace("TOKEN", token);
        return ticketUrl;
    }
}
