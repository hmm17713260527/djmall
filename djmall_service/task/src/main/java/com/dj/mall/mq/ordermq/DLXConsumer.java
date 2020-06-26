package com.dj.mall.mq.ordermq;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.order.OrderApi;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 死信-消费者
 */
//@Component
public class DLXConsumer {


    @Reference
    private OrderApi orderApi;

    /**
     * 消费者
     *
     * @param message 消息体
     * @throws Exception
     */
    @RabbitHandler
    @RabbitListener(queues = "dlx")
    public void process1(Message message) throws Exception {
        String s = new String(message.getBody(), "UTF-8");
        String orderNo = s.split(":")[0];

        orderApi.mqDlx(orderNo);


    }

}
