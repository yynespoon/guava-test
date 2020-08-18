package group.product;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @author lixiaoyu
 * @since 2020-08-18 19:13
 */
public class TestGroupProduct {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("groupProducer");
        defaultMQProducer.setNamesrvAddr("localhost:9876");
        defaultMQProducer.start();

        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setTopic("groupConsumer");
            message.setTags("test");
            message.setBody("groupConsumer".getBytes());
            defaultMQProducer.send(message);
        }

        defaultMQProducer.shutdown();
    }
}
