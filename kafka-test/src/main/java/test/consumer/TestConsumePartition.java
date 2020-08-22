package test.consumer;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.google.common.collect.Lists;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author lixiaoyu
 * @since 2020-08-22 01:08
 *
 * 消费 topic 中指定队列
 */
public class TestConsumePartition {

    static {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger root = loggerContext.getLogger("root");
        root.setLevel(Level.INFO);
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        // 设置 kafka 地址
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 设置反序列化器
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        List<TopicPartition> partitionList = Lists.newArrayList();
        for (PartitionInfo partitionInfo : consumer.partitionsFor("test")) {
            partitionList.add(new TopicPartition("test", partitionInfo.partition()));
            break;
        }
        consumer.assign(partitionList);
        while(true){

            ConsumerRecords<String, String> result = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : result) {
                System.err.println(record.value());
            }
        }
    }
}
