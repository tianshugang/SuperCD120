package com.cd120.gh.tools;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Component
public class HttpTools {

    @Autowired
    ConfigDataTool tool;

    static Map<String ,String> params = new LinkedHashMap<String ,String>();

    static String HOST = "https://hxgyapiv2.cd120.info";
    static {

        //Client
        params.put("Accept-Encoding","gzip");
        params.put("User-Agent","okhttp/3.12.1");

        //Entity
        params.put("Content-Type","application/json; charset=UTF-8");

        //Transport
        params.put("Connection","Keep-Alive");

        //Miscellaneous
        params.put("Client-Version","hyt-android-6.0.4");
    }


    private void addAuthInfo(HttpPost post){
        for(String key : params.keySet()){
            String value = params.get(key);
            post.addHeader(key,value);
        }

        for(String key : tool.getAuthConfig().keySet()){
            String value = tool.getAuthConfig().get(key);
            post.addHeader(key,value);
        }

        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(180 * 1000).setConnectionRequestTimeout(180 * 1000)
                .setSocketTimeout(180 * 1000).setRedirectsEnabled(true).build();
        post.setConfig(requestConfig);
        return;
    }


    private DefaultHttpClient getHttpClient() {
        DefaultHttpClient httpClient = HttpClientUtil.getNewHttpClient();
//        String proxyHost = "10.0.0.9";
//        int proxyPort = 8888;
//        String userName = "";
//        String password = "";
//        httpClient.getCredentialsProvider().setCredentials(
//                new AuthScope(proxyHost, proxyPort),
//                new UsernamePasswordCredentials(userName, password));
//        HttpHost proxy = new HttpHost(proxyHost,proxyPort);
//        httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        return httpClient;
    }

    public String getImageCode(){
        String URL = HOST+"/cloud/hosplatcustomer/customer/image/getimagecode";
        DefaultHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(URL);
        addAuthInfo(httpPost);
        Map<String,String> param = new HashMap<String,String>();
        param.put("type","PATIENT_ANDROID");
        param.put("appCode","HXGYAPP");
        param.put("channelCode","PATIENT_ANDROID");
        try {
            httpPost.setEntity(new StringEntity(JSON.toJSONString(param), ContentType.create("application/json", "utf-8")));
            System.out.println("request parameters" + EntityUtils.toString(httpPost.getEntity()));
            System.out.println("httpPost:" + httpPost);
            HttpResponse response = httpClient.execute(httpPost);
            return processResp(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != httpClient) {
                httpClient.close();
            }
        }
        return "error";
    }

    //查排班
    public String getSchedule(Map<String,String> param){

        param.put("appointmentType","1");
        param.put("hospitalAreaCode","HID0101");
        param.put("hospitalCode","HID0101");
        param.put("appCode","HXGYAPP");
        param.put("channelCode","PATIENT_ANDROID");

        String URL = "https://hytapiv2.cd120.com/cloud/hosplatcustomer/call/appointment/doctorListModel/selDoctorDetails";
        DefaultHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(URL);
        addAuthInfo(httpPost);
        try {
            httpPost.setEntity(new StringEntity(JSON.toJSONString(param), ContentType.create("application/json", "utf-8")));
            System.out.println("request parameters" + EntityUtils.toString(httpPost.getEntity()));
            System.out.println("httpPost:" + httpPost);
            HttpResponse response = httpClient.execute(httpPost);
            return processResp(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != httpClient) {
                httpClient.close();
            }
        }
        return "error";
    }

    //挂号
    public String requestAppointmentNew (Map<String,String> param){

        param.put("appointmentType","1");
        param.put("hospitalAreaCode","HID0101");
        param.put("hospitalCode","HID0101");
        param.put("type","PATIENT_ANDROID");
        param.put("appCode","HXGYAPP");
        param.put("channelCode","PATIENT_ANDROID");


        String URL = "https://hytapiv2.cd120.com/cloud/hosplatcustomer/call/appointment/appointmentModel/sureAppointment";
        DefaultHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(URL);
        addAuthInfo(httpPost);

        try {
            httpPost.setEntity(new StringEntity(JSON.toJSONString(param), ContentType.create("application/json", "utf-8")));
            System.out.println("request parameters" + EntityUtils.toString(httpPost.getEntity()));
            System.out.println("httpPost:" + httpPost);
            HttpResponse response = httpClient.execute(httpPost);
            return processResp(response);
        }catch (Exception e) {
                e.printStackTrace();
        } finally {
            if (null != httpClient) {
                httpClient.close();
            }
        }
        return "error";
    }

    //查询绑定的就诊卡
    public String requestCardList (){
        Map<String,String> param = new HashMap<String,String>();
        param.put("organCode","HID0101");
        param.put("appCode","HXGYAPP");
        param.put("channelCode","PATIENT_ANDROID");

        String URL = HOST+"/cloud/hosplatcustomer/cardservice/cardlist";
        DefaultHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(URL);
        addAuthInfo(httpPost);

        try {
            httpPost.setEntity(new StringEntity(JSON.toJSONString(param), ContentType.create("application/json", "utf-8")));
            System.out.println("request parameters" + EntityUtils.toString(httpPost.getEntity()));
            System.out.println("httpPost:" + httpPost);
            HttpResponse response = httpClient.execute(httpPost);
            return processResp(response);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != httpClient) {
                httpClient.close();
            }
        }
        return "error";
    }

    public String searchDoctorByName (Map<String,String> param){
        param.put("appointmentType","1");
        param.put("haveNo","0");
        param.put("hospitalCode","HID0101");
        param.put("keyWordEnumType","0");
        param.put("regTitelCode","");
        param.put("scheduleDate","");
        param.put("appCode","HXGYAPP");
        param.put("channelCode","PATIENT_ANDROID");
        String URL = "https://hytapiv2.cd120.com/cloud/hosplatcustomer/call/appointment/doctorListModel/selDoctorListByMoreTerm";
        DefaultHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(URL);
        addAuthInfo(httpPost);

        try {
            httpPost.setEntity(new StringEntity(JSON.toJSONString(param), ContentType.create("application/json", "utf-8")));
            System.out.println("request parameters" + EntityUtils.toString(httpPost.getEntity()));
            System.out.println("httpPost:" + httpPost);
            HttpResponse response = httpClient.execute(httpPost);
            return processResp(response);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != httpClient) {
                httpClient.close();
            }
        }
        return "error";
    }
    private String processResp(HttpResponse response){
        if (response != null && response.getStatusLine().getStatusCode() == 200) {
            try {
                HttpEntity entity = response.getEntity();
                if ((entity.getContentEncoding() != null)
                        && entity.getContentEncoding().getValue().contains("gzip")) {
                    GZIPInputStream gzip = new GZIPInputStream(
                            new ByteArrayInputStream(EntityUtils.toByteArray(entity)));
                    InputStreamReader isr = new InputStreamReader(gzip);
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String temp;
                    while ((temp = br.readLine()) != null) {
                        sb.append(temp.replace("\\n", ""));
                        sb.append("\r\n");
                    }
                    isr.close();
                    gzip.close();
                    return sb.toString();
                } else {
                    return EntityUtils.toString(entity);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return "error";
    }
}
