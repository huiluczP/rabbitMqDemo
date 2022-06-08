package com.huiluczp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
// 短信要钱..改成邮箱好了
public class MailUtil {
    private static final String SENDER = "huiluczP";
    private static final String API_URL = "https://api.sendcloud.net/apiv2/mail/sendtemplate";
    private static final String FROM = "970921331@qq.com";
    private static final String TEMPLATE = "template_validCode";

    @Value("${mail.user}")
    private String API_USR;

    @Value("${mail.key}")
    private String API_KEY;

    public boolean sendMail(String mailAddress, String message) throws IOException {
        // 调用接口发送邮件
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(API_URL);

        List<NameValuePair> params = new ArrayList<>();
        // 您需要登录SendCloud创建API_USER，使用API_USER和API_KEY才可以进行邮件的发送。
        params.add(new BasicNameValuePair("apiUser", API_USR));
        params.add(new BasicNameValuePair("apiKey", API_KEY));
        params.add(new BasicNameValuePair("from", FROM));
        params.add(new BasicNameValuePair("fromName", SENDER));
        params.add(new BasicNameValuePair("to", mailAddress));

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, List<String>> codeMap = new HashMap<>();
        List<String> codes = new ArrayList<>();
        codes.add(message);
        codeMap.put("%valid_code%", codes);
        HashMap<String, Object> x_sm = new HashMap<>();
        x_sm.put("sub", codeMap);

        List<String> tos = new ArrayList<>();
        tos.add(mailAddress);
        x_sm.put("to", tos);

        String x_sm_tp_api = mapper.writeValueAsString(x_sm);
        System.out.println(x_sm_tp_api);

        params.add(new BasicNameValuePair("xsmtpapi", x_sm_tp_api));
        params.add(new BasicNameValuePair("templateInvokeName", TEMPLATE));

        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        // 请求
        CloseableHttpResponse response = httpclient.execute(httpPost);
        String result = null;
        boolean over = false;
        // 处理响应
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) { // 正常返回
            result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
            httpPost.releaseConnection();

            Map<String, Object> map = mapper.readValue(result, HashMap.class);
            return (boolean) map.get("result");
        } else {
            System.err.println("error");
            httpPost.releaseConnection();
            return false;
        }
    }
}
