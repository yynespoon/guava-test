package test.publisher;

import com.google.common.collect.Maps;
import com.rabbitmq.client.*;
import test.ConnectionUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author lixiaoyu
 * @since 2020-08-19 22:41
 */
public class TestBackupExchangePublisher {

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.createConnection();
        Channel channel = connection.createChannel();
        channel.confirmSelect();

        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("send success");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("send error");

            }
        });

        channel.addReturnListener(new ReturnCallback() {
            @Override
            public void handle(Return returnMessage) {
                System.out.println(returnMessage.getReplyText());
            }
        });
        Map<String, Object> properties = Maps.newHashMap();
        properties.put("alternate-exchange", "testBackupExchange");
        channel.exchangeDeclare("testMainExchange", BuiltinExchangeType.TOPIC, false, false, properties);
        channel.queueBind("testQueue", "testMainExchange", "test1.#");
        channel.queueBind("testQueue2", "testMainExchange", "test2.#");

        channel.exchangeDeclare("testBackupExchange", BuiltinExchangeType.FANOUT);
        channel.queueBind("testQueue2", "testBackupExchange", "");



        channel.basicPublish("testMainExchange", "test2123123", true, null, "testBackupExchange".getBytes());
    }
}
