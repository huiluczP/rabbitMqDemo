package com.huiluczp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.huiluczp.bean.response.CommonResponse;
import com.huiluczp.config.RabbitInfoConfig;
import com.huiluczp.config.RedisConfig;
import com.huiluczp.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InfoController {
    @Autowired
    private InfoService infoService;

    @RequestMapping("/main")
    public String main(){
        return "valid.html";
    }

    @ResponseBody
    @RequestMapping("/sendMessage")
    public CommonResponse sendMessage(String phoneNumber) throws JsonProcessingException {
        // 将信息放到message中并传给对应队列
        String validCode = InfoService.createRandomMessage();
        System.out.println(phoneNumber);
        String message = infoService.createMessage(phoneNumber, validCode);
        System.out.println(message);
        infoService.sendMessage(RabbitInfoConfig.EXCHANGE, RabbitInfoConfig.ROUTING, message);
        return new CommonResponse(true, "成功发送验证码请求");
    }

    @ResponseBody
    @RequestMapping("/validMessage")
    public CommonResponse validMessage(String phoneNumber, String code){
        System.out.println(phoneNumber);
        System.out.println(code);
        boolean result = infoService.checkValidCode(phoneNumber, code);
        if(result){
            return new CommonResponse(true, "成功验证验证码");
        }else{
            return new CommonResponse(false, "验证码错误或失效");
        }
    }
}
