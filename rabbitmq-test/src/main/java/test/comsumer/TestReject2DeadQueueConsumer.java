package test.comsumer;

import com.rabbitmq.client.*;
import test.ConnectionUtils;

import java.io.IOException;

/**
 * @author lixiaoyu
 * @since 2020-08-20 14:42
 */
public class TestReject2DeadQueueConsumer {

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.createConnection();
        Channel channel = connection.createChannel();

        channel.basicConsume("rejectQueue", false, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        });

        channel.basicConsume("deadQueue", true, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println(new String(message.getBody()));
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        });
    }
}
