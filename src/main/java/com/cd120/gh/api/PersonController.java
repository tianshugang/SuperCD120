package com.cd120.gh.api;

import com.cd120.gh.tools.ConfigDataTool;
import com.cd120.gh.tools.HttpTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RestController注解能够使项目支持Rest
@RestController
@Component
public class PersonController {

    @Autowired
    HttpTools httptool;

    @Autowired
    ConfigDataTool tool;

    @RequestMapping(value = "/card/search", method = RequestMethod.GET)
    String searchCARDs(){
        return httptool.requestCardList();
    }

    @RequestMapping(value = "/card/guahao", method = RequestMethod.PUT)
    String Guahao(@RequestParam(value = "name") String name){
        return "Hello " + name;
    }

}
