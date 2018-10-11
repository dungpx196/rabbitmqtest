
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

    //private final static String QUEUE_NAME = "hello";
    private final static String QUEUE_NAME = "dungpx3";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Queue";
            for (int i = 0; i < 10000; ++i) {
                channel.basicPublish("", QUEUE_NAME, null, ("{\n"
                        + "    \"queue no\": "+i+",\n"
                        + "	\"responseCode\": 200,\n"
                        + "    \"message\": \"Success\",\n"
                        + "    \"isSubsribed\": \"0\",\n"
                        + "    \"isShowSubscribeWindow\": \"1\"\n"
                        + "}").getBytes("UTF-8"));
            }
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
