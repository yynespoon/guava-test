package test.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import test.ConnectionUtils;

import java.io.IOException;

/**
 * @author lixiaoyu
 * @since 2020-08-19 15:21
 */
public class TestPublisher {

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.createConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("testQueue", true, false, false, null);
        for (int i = 0; i < 100; i++) {
            channel.basicPublish("", "testQueue", null, "hello world".getBytes());
        }
        channel.close();
        connection.close();
    }
}
