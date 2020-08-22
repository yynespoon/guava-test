package test.producer;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author lixiaoyu
 * @since 2020-08-21 00:57
 */
public class TestProducer {

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
        properties.put("key.serializer", StringSerializer.class.getName());
        //消息序列化器
        properties.put("value.serializer", StringSerializer.class.getName());
        // 当全部节点同步完成时才返回结果
        properties.put("acks", "all");

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("test", "1", "hello world");
        kafkaProducer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                System.out.println("------------" + recordMetadata.partition() + " " + recordMetadata.offset());
            }
        });
        kafkaProducer.flush();
        kafkaProducer.close();

    }
}
