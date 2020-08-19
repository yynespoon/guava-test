package test.publisher;

import com.rabbitmq.client.*;
import test.ConnectionUtils;

import java.io.IOException;

/**
 * @author lixiaoyu
 * @since 2020-08-19 22:15
 */
public class TestCallBackPublisher {

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.createConnection();
        Channel channel = connection.createChannel();
        //到达 exchange 的确认消息回调需要开启这个参数
        channel.confirmSelect();

        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println(deliveryTag + " send success");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println(deliveryTag + " send fail");
            }
        });

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(replyCode + " " + replyText + " " + routingKey);
            }
        });
        // exchange 路由失败消息回调需要设置 mandatory 为 true
        channel.basicPublish("", "testQueue4", true, null, "testTransaction".getBytes());
    }
}
