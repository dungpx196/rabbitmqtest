
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv {

    //private final static String QUEUE_NAME = "hello";
    private final static String QUEUE_NAME = "dungpx3";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        //Channel channel = connection.createChannel();
        Channel channel = connection.createChannel(123);

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        //channel.basicConsume(QUEUE_NAME, false, consumer);
        channel.basicConsume(QUEUE_NAME, true, consumer);
        //channel.basicAck(1, false); // acknowledge receipt of the message
//        boolean autoAck = false;
//        GetResponse response = channel.basicGet(QUEUE_NAME, autoAck);
//        if (response == null) {
//            // No message retrieved.
//        } else {
//            AMQP.BasicProperties props = response.getProps();
//            byte[] body = response.getBody();
//            long deliveryTag = response.getEnvelope().getDeliveryTag();
//            channel.basicAck(1, false); // acknowledge receipt of the message
//        }

    }
}
