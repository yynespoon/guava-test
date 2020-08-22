package test.consumer;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

/**
 * @author lixiaoyu
 * @since 2020-08-21 00:57
 */
public class TestConsumer {

    static {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger root = loggerContext.getLogger("root");
        root.setLevel(Level.INFO);
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        // kafka server addr
        properties.put("bootstrap.servers", "localhost:9092");
        //key 序列化器 用来路由 partition
        properties.put("key.deserializer", StringDeserializer.class.getName());
        //消息序列化器
        properties.put("value.deserializer", StringDeserializer.class.getName());
        //定义消费者群组
        properties.put("group.id", "testConsumerGroup");

        KafkaConsumer<String, String> consumer = new KafkaConsumer(properties);
        consumer.subscribe(Collections.singletonList("test"));

        while(true){

            ConsumerRecords<String, String> result = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : result) {
                System.err.println(record.value());
            }
        }

    }
}
