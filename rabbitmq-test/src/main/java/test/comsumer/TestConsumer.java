package test.comsumer;

import com.rabbitmq.client.*;
import test.ConnectionUtils;

import java.io.IOException;

/**
 * @author lixiaoyu
 * @since 2020-08-19 15:21
 */
public class TestConsumer {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.createConnection();
        Channel channel = connection.createChannel();

        channel.basicConsume("testQueue2", false, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println(new String(message.getBody()));
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        });
    }
}
