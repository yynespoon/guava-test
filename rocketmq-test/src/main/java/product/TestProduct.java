package product;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author lixiaoyu
 * @since 2020-08-17 20:22
 */
public class TestProduct {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("testMqProducer");
        defaultMQProducer.setNamesrvAddr("localhost:9876");
        defaultMQProducer.start();

        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setBody("hello world".getBytes());
            message.setTopic("testTopic");
            message.setTags("testTags");
            SendResult send = defaultMQProducer.send(message);
            System.out.println(send.getSendStatus());
        }

        defaultMQProducer.shutdown();
    }
}
