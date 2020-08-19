package test.publisher;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import test.ConnectionUtils;

/**
 * @author lixiaoyu
 * @since 2020-08-19 17:50
 */
public class TestTopicExchangePublisher {

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.createConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDelete("topicExchanger");
        channel.exchangeDeclare("topicExchanger", BuiltinExchangeType.TOPIC);
        channel.queueBind("testQueue", "topicExchanger", "test1.#");
        channel.queueBind("testQueue2", "topicExchanger", "test2.#");

        channel.basicPublish("topicExchanger", "test1", null, "test1".getBytes());
        channel.basicPublish("topicExchanger", "test2", null, "test2".getBytes());

        channel.close();
        connection.close();
    }
}
