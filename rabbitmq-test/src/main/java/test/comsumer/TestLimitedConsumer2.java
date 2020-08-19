package test.comsumer;

import com.rabbitmq.client.*;
import test.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lixiaoyu
 * @since 2020-08-20 00:09
 */
public class TestLimitedConsumer2 {

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.createConnection();
        Channel channel = connection.createChannel();

        channel.basicQos(50);
        channel.basicConsume("testQueue", false, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println(message.getEnvelope().getDeliveryTag());
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        });
    }
}
