
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Send2 {

    //private final static String QUEUE_NAME = "hello";
    private final static String QUEUE_NAME = "dungpx4";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {

            //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            String message = "Queue";
            for (int i = 0; i < 10000; ++i) {
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, (""+i+"").getBytes("UTF-8"));
            }
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
