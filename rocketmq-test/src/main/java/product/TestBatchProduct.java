package product;

import com.google.common.collect.Lists;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.List;

/**
 * @author lixiaoyu
 * @since 2020-08-18 15:15
 */
public class TestBatchProduct {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("testBatchProducer");
        defaultMQProducer.setNamesrvAddr("localhost:9876");
        defaultMQProducer.start();
        List<Message> messageList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setTopic("batchMessage");
            message.setTags("test");
            message.setBody("batchMessage".getBytes());
            messageList.add(message);
        }

        // 发送消息单条不能超过 4M 不然会报错
        // 单条消息最好不要大于 1M 会影响性能
        Message message = new Message();
        message.setTopic("batchMessage");
        message.setTags("test");
        message.setBody(new byte[1024 * 1024 * 5]);

        messageList.add(message);
        System.out.println(defaultMQProducer.send(messageList));
        defaultMQProducer.shutdown();
    }
}
