package com.zds.scf.web.common;

import com.zds.common.util.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

;

public class PayRequest {

    private static Logger log = LoggerFactory.getLogger(PayRequest.class);

    public static final int bufferSize = 1024;
    private static SSLSocketFactory ssf = null;

    static {
        init();
    }


    private static void init() {
    /*    try {
            // 证书密码（默认为商户ID）
            String password = WeChatPayProperties.getCertPassword();
            // 实例化密钥库
            KeyStore ks = KeyStore.getInstance("PKCS12");
            // 获得密钥库文件流
            Resource key = ApplicationContextHolder.get().getResource(WeChatPayProperties.getCertLocalPath());
            if (!key.exists()) {
                throw Exceptions.newRuntimeException("密钥文件不存在：" + WeChatPayProperties.getCertLocalPath());
            }
            // 加载密钥库
            ks.load(key.getInputStream(), password.toCharArray());
            // 实例化密钥库
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            // 初始化密钥工厂
            kmf.init(ks, password.toCharArray());

            // 创建SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
            // 获取SSLSocketFactory对象
            ssf = sslContext.getSocketFactory();
        } catch (Exception e) {
            throw Exceptions.newRuntimeException(e);
        }*/
    }

    /**
     * @param url 请求url
     * @return
     * @author tanbin http请求
     */
    public static String post(String url) {
        return post(url, null, null);
    }

    /**
     * @param url    请求url
     * @param params 将参数封装成map
     * @return
     * @author tanbin http请求
     */
    public static String post(String url, Map<String, String> params) {
        return post(url, params, null);
    }

    /**
     * post请求
     *
     * @param url     地址
     * @param params  参数
     * @param timeout 超时
     * @return
     */
    public static String post(String url, Map<String, String> params,
                              Integer timeout) {
        return post(url, params, timeout, true);
    }

    /**
     * post请求
     *
     * @param url     地址
     * @param params  参数
     * @param timeout 超时
     * @return
     */
    public static String post(String url, Map<String, String> params,
                              Integer timeout, boolean debug) {
        Map<String, String> opts = new HashMap<String, String>();
        if (timeout != null) {
            opts.put("timeout", timeout.toString());
        }
        opts.put("debug", Boolean.valueOf(debug).toString());
        opts.put("charsetName", "UTF-8");
        opts.put("postType", "POST");
        return httpSendData(url, params, opts);
    }

    public static String get(String url, Map<String, String> params,
                             Integer timeout, boolean debug) {
        Map<String, String> opts = new HashMap<String, String>();
        if (timeout != null) {
            opts.put("timeout", timeout.toString());
        }
        opts.put("debug", Boolean.valueOf(debug).toString());
        opts.put("charsetName", "UTF-8");
        opts.put("postType", "GET");
        return httpSendData(url, params, opts);
    }


    /**
     * http 发送消息 最新推荐用法
     *
     * @param url
     * @param params
     * @param opts   debug timeout charsetName
     * @return
     */
    public static String httpSendData(String url, Map<String, String> params, Map<String, String> opts) {

        // 构建请求参数
        StringBuffer sb = new StringBuffer();
        if (params != null) {
            Set<Entry<String, String>> key = params.entrySet();
            for (Iterator<Entry<String, String>> it = key.iterator(); it
                    .hasNext(); ) {
                Entry<String, String> entry = it.next();
                sb.append(entry.getKey());
                sb.append("=");
                String v = entry.getValue();
                if (v != null) {
                    sb.append(v);
                }
                if (it.hasNext()) {
                    sb.append("&");
                }
            }
        }
        return httpSendData(url, sb.toString(), opts);
    }

    /**
     * 付款或查询post
     *
     * @param url
     * @param paramStr
     * @param opts     key:debug,timeout,charsetName
     * @return
     */
    public static String httpSendData(String url, String paramStr, Map<String, String> opts) {
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("url不能为空");
        }
        String tmp = httpSendBase(url, paramStr, opts, null);
        return tmp;
    }

    /**
     * 退款ssl post 证书加密
     *
     * @param url
     * @param paramStr
     * @param opts
     * @return
     */
    public static String httpSendSSLData(String url, String paramStr, Map<String, String> opts) {
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("url不能为空");
        }
        String tmp = httpSendSSLBase(url, paramStr, opts, null);
        return tmp;
    }


    public static String httpSendSSLBase(String url, String paramStr,
                                         Map<String, String> opts, Map<String, String> head) {
        Boolean debug = true;
        Integer timeout = null;
        String charsetName = "UTF-8";
        URL u = null;
        HttpsURLConnection con = null;
        // 构建请求参数
        StringBuffer sb = new StringBuffer();
        OutputStreamWriter osw = null;
        OutputStream os = null;
        InputStream is = null;
        String tmp = null;
        // 尝试发送请求
        try {
            if (paramStr != null) {
                sb.append(paramStr);
            }
            if (opts != null && opts.size() != 0) {
                String debugStr = opts.get("debug");
                if (debugStr != null) {
                    debug = Boolean.valueOf(debugStr);
                }
                String timeoutStr = opts.get("timeout");
                if (timeoutStr != null) {
                    timeout = Integer.valueOf(timeoutStr);
                }
                charsetName = opts.get("charsetName");
            }


            u = new URL(url);
            con = (HttpsURLConnection) u.openConnection();
            if (timeout != null) {
                con.setConnectTimeout(1000 * timeout);
                con.setReadTimeout(1000 * timeout);
            } else {
                con.setConnectTimeout(1000 * 15);
                con.setReadTimeout(1000 * 15);
            }
            con.setRequestMethod(opts == null || opts.get("postType") == null ? "POST" : opts.get("postType"));
            con.setSSLSocketFactory(ssf);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            con.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0");
            if (head != null && !head.isEmpty()) {
                for (Entry<String, String> e : head.entrySet()) {
                    con.setRequestProperty(e.getKey(), e.getValue());
                }
            }
            os = con.getOutputStream();
            osw = new OutputStreamWriter(os, charsetName);
            osw.write(sb.toString());
            osw.flush();
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    log.error("{}", e.getMessage(), e);
                }
            }
            // 读取返回内容
            is = con.getInputStream();
            tmp = readInputStream(is, charsetName);
            if (debug) {
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
            throw new RuntimeException("url网络异常:" + url + "\r\n" + "参数："
                    + sb);
        } finally {
            close(con, os, is);
        }
        return tmp;
    }

    public static String httpSendBase(String url, String paramStr,
                                      Map<String, String> opts, Map<String, String> head) {
        Boolean debug = true;
        Integer timeout = null;
        String charsetName = "UTF-8";
        URL u = null;
        HttpURLConnection con = null;
        // 构建请求参数
        StringBuffer sb = new StringBuffer();
        OutputStreamWriter osw = null;
        OutputStream os = null;
        InputStream is = null;
        String tmp = null;
        // 尝试发送请求
        try {
            if (paramStr != null) {
                sb.append(paramStr);
            }
            if (opts != null && opts.size() != 0) {
                String debugStr = opts.get("debug");
                if (debugStr != null) {
                    debug = Boolean.valueOf(debugStr);
                }
                String timeoutStr = opts.get("timeout");
                if (timeoutStr != null) {
                    timeout = Integer.valueOf(timeoutStr);
                }
                charsetName = opts.get("charsetName");
            }
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            if (timeout != null) {
                con.setConnectTimeout(1000 * timeout);
                con.setReadTimeout(1000 * timeout);
            } else {
                con.setConnectTimeout(1000 * 15);
                con.setReadTimeout(1000 * 15);
            }
            con.setRequestMethod(opts == null || opts.get("postType") == null ? "POST" : opts.get("postType"));
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            con.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0");
            if (head != null && !head.isEmpty()) {
                for (Entry<String, String> e : head.entrySet()) {
                    con.setRequestProperty(e.getKey(), e.getValue());
                }
            }
            os = con.getOutputStream();
            osw = new OutputStreamWriter(os, charsetName);
            osw.write(sb.toString());
            osw.flush();
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    log.error("{}", e.getMessage(), e);
                }
            }
            // 读取返回内容
            is = con.getInputStream();
            tmp = readInputStream(is, charsetName);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
            throw new RuntimeException("url网络异常:" + url + "\r\n" + "参数："
                    + sb);
        } finally {
            close(con, os, is);
        }
        return tmp;
    }

    private static void close(HttpURLConnection con, OutputStream os,
                              InputStream is) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                log.error("{}", e.getMessage(), e);
            }
        }
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                log.error("{}", e.getMessage(), e);
            }
        }
        if (con != null) {
            try {
                con.disconnect();
            } catch (Exception e) {
                log.error("{}", e.getMessage(), e);
            }
        }
    }

    public static String readInputStream(InputStream inStream,
                                         String charsetName) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        String data = null;
        try {
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            data = outStream.toString(charsetName);// 网页的二进制数据
            return data;
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
            return data;
        } finally {
            outStream.close();
            inStream.close();
        }
    }
    /**
     *httpclient  post json
     * @param url
     * @param param
     * @return
     */
    public static String doHttpClientPost(String url,String param){
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		try {
		    StringEntity stringEntity = new StringEntity(param, "utf-8");
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");//发送json数据需要设置contentType
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();//设置请求和传输超时时间
            post.setConfig(requestConfig);
            post.setEntity(stringEntity);
			HttpResponse res = client.execute(post);
			if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String result = EntityUtils.toString(res.getEntity());
				return result;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return "";
	}
}
