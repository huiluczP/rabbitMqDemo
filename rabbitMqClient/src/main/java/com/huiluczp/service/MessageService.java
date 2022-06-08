package com.huiluczp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huiluczp.bean.Message;
import com.huiluczp.util.MailUtil;
import com.huiluczp.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class MessageService {
    @Autowired
    RedisUtil redisUtil;

    @Value("${spring.redis.timeout}")
    private long timeout;

    @Autowired
    MailUtil mailUtil;

    // 获取message并解析，短信发送并存储到redis
    public boolean sendMessage(String messageJson) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(messageJson);
        Message message = objectMapper.readValue(messageJson, Message.class);

        // 发送邮件
        if(!mailUtil.sendMail(message.getPhoneNumber(), message.getMessage())){
            System.out.println("邮件发送失败");
            return false;
        }

        // 成功则存入redis
        redisUtil.add(message.getPhoneNumber(), message.getMessage(), timeout, TimeUnit.SECONDS);
        System.out.println("redis成功存储");
        return true;
    }
}
