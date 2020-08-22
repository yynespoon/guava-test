package test.publisher;

import com.google.common.collect.Maps;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import test.ConnectionUtils;

import java.util.HashMap;

/**
 * @author lixiaoyu
 * @since 2020-08-20 14:27
 */
public class TestDeadExchangePublisher {

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.createConnection();
        Channel channel = connection.createChannel();

        HashMap<String, Object> argsMap = Maps.newHashMap();
        //给队列设置死信交换机
        argsMap.put("x-dead-letter-exchange", "deadQueueExchange");
        //消息被拒绝后 routeKey 的路由变更
        argsMap.put("x-dead-letter-routing-key", "key.dead");

        channel.queueDeclare("rejectQueue", true, false, false, argsMap);
        channel.queueDeclare("deadQueue", true, false, false, null);
        channel.exchangeDeclare("deadQueueExchange", BuiltinExchangeType.DIRECT);
        channel.queueBind("deadQueue", "deadQueueExchange", "key.dead");

        channel.basicPublish("", "rejectQueue", null, "rejectMsg".getBytes());

        channel.close();
        connection.close();
    }
}
