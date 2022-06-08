package com.huiluczp.receiver;

import com.huiluczp.config.RabbitInfoConfig;
import com.huiluczp.service.MessageService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = RabbitInfoConfig.QUEUE, ackMode = "MANUAL")
// 手动ack
public class MessageReceiver {
    @Autowired
    MessageService messageService;

    // 监听方法，获取消息队列中信息
    // redis存储，短信发送
    @RabbitHandler
    public void sendPhoneMessage(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException, InterruptedException {
        boolean isOk = messageService.sendMessage(message);
        // Thread.sleep(1000);
        // 想了下不管成不成功都直接消费掉吧
        if(isOk) {
            channel.basicAck(deliveryTag, false);
            System.out.println("成功完成验证码发送");
        }
        else {
            channel.basicAck(deliveryTag, false);
            System.out.println("发送失败");
        }
    }
}
