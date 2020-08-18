package group.product;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author lixiaoyu
 * @since 2020-08-18 19:34
 */
public class TestTransactionGroup1 {

    public static void main(String[] args) throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("transactionProducer");
        producer.setNamesrvAddr("localhost:9876");
        producer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                String s = new String(msg.getBody());
                switch (s){
                    case "1":
                        return LocalTransactionState.COMMIT_MESSAGE;
                    case "2":
                        return LocalTransactionState.ROLLBACK_MESSAGE;
                    default:
                        return LocalTransactionState.UNKNOW;
                }
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                String s = new String(msg.getBody());
                switch (s){
                    case "2":
                        return LocalTransactionState.ROLLBACK_MESSAGE;
                    default:
                        return LocalTransactionState.COMMIT_MESSAGE;
                }
            }
        });
        producer.start();

        for (int i = 0; i < 3; i++) {
            Message message = new Message();
            message.setTopic("transactionMessageGroup");
            message.setTags("test");
            message.setBody(String.valueOf(i).getBytes());
            producer.sendMessageInTransaction(message, null);
        }
    }
}
