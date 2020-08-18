package product;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @author lixiaoyu
 * @since 2020-08-18 15:31
 */
public class TestDelayMessageProduct {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("delayMessageProducer");
        defaultMQProducer.setNamesrvAddr("localhost:9876");
        defaultMQProducer.start();

        Message message = new Message();
        message.setDelayTimeLevel(3);
        message.setTopic("delayMessage");
        message.setTags("test");
        message.setBody("testDelayMessage".getBytes());

        defaultMQProducer.send(message);
        System.out.println("sending");
        defaultMQProducer.shutdown();
    }
}
