package com.cd120.gh.api;

import com.cd120.gh.tools.ConfigDataTool;
import com.cd120.gh.tools.HttpTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//@RestController注解能够使项目支持Rest
@RestController
@Component
public class DoctorController {

    @Autowired
    HttpTools httptool;

    @Autowired
    ConfigDataTool tool;

    @RequestMapping(value = "/doctor/list", method = RequestMethod.GET)
    String listDoctors(){
        return tool.getDoctorConfig();
    }

    @RequestMapping(value = "/doctor/search", method = RequestMethod.GET)
    String searchDoctors(String keyWord){
        Map<String,String> param = new HashMap<String, String>();
        param.put("keyWord",keyWord);
        return httptool.searchDoctorByName(param);
    }

    @RequestMapping(value = "/doctor/schedule", method = RequestMethod.POST)
    String getAppointSchedule(@RequestBody Map<String,String> param){
        System.out.println("getSchedule");
        System.out.println(param.size());
        return httptool.getSchedule(param);
    }

    @RequestMapping(value = "/doctor/requestAppointmentNew", method = RequestMethod.POST)
    String requestAppointmentNew(@RequestBody Map<String,String> param){
        System.out.println("requestAppointmentNew");
        System.out.println(param);
        return httptool.requestAppointmentNew(param);
    }



}
