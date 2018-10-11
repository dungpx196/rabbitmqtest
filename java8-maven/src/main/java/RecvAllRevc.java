
import com.rabbitmq.client.*;

import java.io.IOException;

public class RecvAllRevc {

    //private final static String QUEUE_NAME = "hello";
    private final static String QUEUE_NAME = "dungpx4";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            boolean autoAck = false;
            channel.basicConsume(QUEUE_NAME, autoAck, "tag1",
                    new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag,
                        Envelope envelope,
                        AMQP.BasicProperties properties,
                        byte[] body)
                        throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    long deliveryTag = envelope.getDeliveryTag();
                    // (process the message components here ...)
                    String message = new String(body, "UTF-8");
                    //System.out.println(" [x] Received '" + message + "'");
                    if (Integer.valueOf(message) >= 3000) {
                        channel.basicAck(deliveryTag, false);
                        System.out.println(" [x] Just Acked: '" + message + "'");
                    } else {
                        System.out.println(" [x] Remain: '" + message + "'");
                    }
                }
            });
            Thread.sleep(30000);
            //close channel to release unacked message
            channel.close();
            connection.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
