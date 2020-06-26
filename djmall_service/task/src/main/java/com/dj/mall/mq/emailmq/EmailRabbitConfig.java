package com.dj.mall.mq.emailmq;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mq.emailmq
 * @ClassName: EmailRabbitConfig
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/10 15:58
 * @Version: 1.0
 */
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 单播-配置
 */
//@Configuration
public class EmailRabbitConfig {



    /**
     * 创建队列-队列1
     *
     * @return
     */
    @Bean
    public Queue emailQueue() {
        // name=队列的名称
        // durable 是否持久化
        // exclusive 是否独占队列
        // autoDelete 是否自动删除
        return new Queue("emailQueue", true);
    }


    /**
     * 创建单播路由
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct");
    }

    /**
     * 队列绑定1
     *
     * @return
     */
    @Bean
    public Binding bindingDirectQueue1() {
        // Message的routingKey与其一致 单播建议与队列名称一致
        return BindingBuilder.bind(emailQueue()).to(directExchange()).with("emailQueue");
    }


}
