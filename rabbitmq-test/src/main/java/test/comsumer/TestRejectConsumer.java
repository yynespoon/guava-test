package test.comsumer;

import com.rabbitmq.client.*;
import test.ConnectionUtils;

import java.io.IOException;

/**
 * @author lixiaoyu
 * @since 2020-08-20 00:16
 */
public class TestRejectConsumer {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.createConnection();
        Channel channel = connection.createChannel();

        channel.basicQos(50);
        channel.basicConsume("testQueue", false, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println(message.getEnvelope().getDeliveryTag());
                //拒绝后重回队列
                //不支持批量
                channel.basicReject(message.getEnvelope().getDeliveryTag(), true);
                //支持批量
                channel.basicNack(message.getEnvelope().getDeliveryTag(), true, true);
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        });
    }
}
