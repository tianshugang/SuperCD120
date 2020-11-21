package com.cd120.gh.tools;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Map;


@Component
public class ConfigDataTool {
    @Value("classpath:static/config/doctors.json")
    private Resource doctorsConfig;

    @Value("classpath:static/config/card.json")
    private Resource cardConfig;

    @Value("classpath:static/config/auth.json")
    private Resource authConfig;

//    public String getCARDConfig(){
//        try {
//            return IOUtils.toString(cardConfig.getInputStream(), "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    public String getDoctorConfig(){
        try {
            return IOUtils.toString(doctorsConfig.getInputStream(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Map<String,String> getAuthConfig(){
        try {
            String authStr = IOUtils.toString(authConfig.getInputStream(), "UTF-8");
            Map<String,String> authInfo = JSON.parseObject(authStr,Map.class);
            return authInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
