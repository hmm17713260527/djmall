package com.dj.mall.mq.emailmq;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mq.emailmq
 * @ClassName: EmailConsumer
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/10 15:57
 * @Version: 1.0
 */
import com.alibaba.fastjson.JSON;
import com.dj.mall.model.util.EmailUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.dj.mall.model.base.SystemConstant;

/**
 * 单播-消费者
 */
@Component
public class EmailConsumer {



    /**
     * 消费者1
     * @param jsonObject 消息体
     * @throws Exception
     */
    @RabbitHandler
    @RabbitListener(queues = "emailQueue")
    public void process1(Message message) throws Exception {

        //获取json数据
        JSONObject jsonObject = JSON.parseObject(new String(message.getBody(), "UTF-8"));
        //获取json数据中的id值
        String email = jsonObject.getString("email");

        EmailUtil.sendEmail(email, SystemConstant.STRING_EMAIL, SystemConstant.EMAIL_ADD_CODE, 0);

    }
}
