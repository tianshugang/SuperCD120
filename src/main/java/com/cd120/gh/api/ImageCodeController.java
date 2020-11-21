package com.cd120.gh.api;

import com.cd120.gh.tools.HttpTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@RestController注解能够使项目支持Rest
@RestController
@Component
public class ImageCodeController {

    @Autowired
    HttpTools tool;

    @RequestMapping(value = "/imagecode", method = RequestMethod.GET)
    String getImageCode(){
        String imageCode = tool.getImageCode();
        System.out.println("imageCode:"+imageCode);
        return imageCode;
    }
}
