package com.huiluczp.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huiluczp.bean.Message;
import com.huiluczp.util.RedisUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class InfoService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisUtil redisUtil;

    // 向消息队列发送消息
    public boolean sendMessage(String exchange, String routing, Object message){
        rabbitTemplate.convertAndSend(exchange, routing, message);
        return true;
    }

    // 生成随机六位数字
    public static String createRandomMessage(){
        int num = (int) ((Math.random() * 9 + 1) * 100000);
        return String.valueOf(num);
    }

    // 构建短信对象并传递json
    public String createMessage(String phoneNumber, String validCode) throws JsonProcessingException {
        Message m = new Message(phoneNumber, validCode);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(m);
    }

    // 读取redis中信息并验证
    public boolean checkValidCode(String phoneNumber, String inputValidCode){
        String validCode = redisUtil.get(phoneNumber);
        if(validCode==null)
            return false;
        inputValidCode = JSON.toJSONString(inputValidCode);
        if(inputValidCode.equals(validCode)){
            System.out.println(String.format("验证码正确 号码:%s，验证码:%s", phoneNumber, validCode));
            return true;
        }else{
            System.out.println(validCode);
            System.out.println(String.format("验证码错误 号码:%s", phoneNumber));
            return false;
        }
    }
}
