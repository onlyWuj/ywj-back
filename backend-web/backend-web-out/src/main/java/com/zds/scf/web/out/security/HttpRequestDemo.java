package com.zds.scf.web.out.security;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

public class HttpRequestDemo {
    public static void main(String[] args) throws Exception {
        //用GET请求做演示
        String url = "http://120.55.182.83:8088/out/ywj/device/listByPhone.json?phone=13368023003";
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);//
        client.getParams().setSoTimeout(3000);//
        client.getParams().setConnectionManagerTimeout(1000);
        GetMethod method = new GetMethod(url);
        //fixedHeader(method);
        randomHeader(method);
        try {
            //method.setRequestBody(new ByteArrayInputStream(json.getBytes("UTF-8")));
            //method.setRequestBody(json);
            int status = client.executeMethod(method);
            String res = method.getResponseBodyAsString();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
    }

    //用写死的字符串做演示
    public static void fixedHeader(GetMethod method){
        String key = "123456";
        String encryptKey = "ibaM8iDoPP13jm5zHwlKmEviMYGxzSKwLHvNy9kkSB5SrIdWJj/4hAzLKv7l/rKhHtaHiR1RcL173Bmz7OILg/ZqCKXdL7UPGXhPrp6MNJ/n61gWOQBe1tD4clBsAQkuhEpcf3lyXte4dLtyEQLNMzapF4x8XD1ioaJ7CWcceRhTY13PIK1pnspZGa0pCdLN4RnBR8BvkCQzcKRb1SciWOscwtx/MDKPJ5Ehma1FUdxRdtaiNa+KzqW0Q188ms8SymWryzPGLSdMBmuI8ypg6KMMGWQko1C3UABXyMpDpagG60Jw/DFSkoLucTSDSFgKGiUTIElpR4mMzccVoxBmIA==";
        method.setRequestHeader("ywj_out_token",key+"_"+encryptKey);
    }

    //正式场合用随机字符串做
    public static void randomHeader(GetMethod method) throws Exception {

        //获取6位随机字符串
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        String key = sb.toString();

        //公钥字符串
        String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjZZMJpNda1/Wvr22h02sobcaZ7fi2hwYHWuGu4RlsLBhQYcaf7yfOC3Wv6KV5xh+o2fA8CTYH1MJ99xyjBLDv0BRnqD8fOXxpbRtj7S197BQo6V1wPtt+iLSwCgdC75TcudBJzSmqellva/hJ0wx/yezQOqpWxY1Tgi3fk7It4Mab/T3ac6YYjfkuzJ6McdV/4l1KYC++WvOC1z7as5GSUuVGO+8/GZkyLYRNXRP8lmAfhWwQvX9rSISTNK4mfVFGuPCard0R+ExCHLceNPgcPRoy1ZOw8hu6QykdLT66iTi+0dZz+6awudOTJ6yafvG//36N/lx7jmgzWi+oO4+RwIDAQABFbxRvlmN0O/y3GWwLCW/j2SndBLrIvO/rV1zl+i8Pnxe0rWQqDfnujYkG9HuzJhLKQEg9EGRQ=";

        //生成公钥对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec( Base64.decodeBase64(publicKeyStr));
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);

        //将生成的随机字符串加密后是byte数组
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptKeyBytes = cipher.doFinal(key.getBytes());

        //转化成base64编码形式字符串
        String encryptKey = Base64.encodeBase64String(encryptKeyBytes);

        //添加到header中
        method.setRequestHeader("ywj_out_token",key+"_"+encryptKey);
    }
}