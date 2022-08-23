package co.epam.jmstask1orderservice;

import co.epam.jmstask1orderservice.core.pojos.Order;
import co.epam.jmstask1orderservice.services.jms.OrderSenderService;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ByteArrayResource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class JmsTask1OrderServiceApplication implements CommandLineRunner {

    @Autowired
    OrderSenderService orderSenderService;
    @Autowired
    AmazonSQS amazonSQS;
    private static final Logger LOGGER = LoggerFactory.getLogger(JmsTask1OrderServiceApplication.class);
    @Value("${mocks.order-mock}")
    private String MOCK_FILENAME = "mock.json";
    public static final String RESOURCES_PATH = "src/main/resources/";
    @Value("${queues.processed-order}")
    public String PROCESSED_ORDER_QUEUE;
    @Value("${queues.order-queue}")
    public String ORDER_QUEUE;
    @Value("${queues.log-changes-queue}")
    public String LOG_CHANGES_QUEUE;


    public static void main(String[] args) {
        SpringApplication.run(JmsTask1OrderServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LOGGER.info("Creating default queues...");
        amazonSQS.createQueue(new CreateQueueRequest(ORDER_QUEUE));
        amazonSQS.createQueue(new CreateQueueRequest(PROCESSED_ORDER_QUEUE));
        amazonSQS.createQueue(new CreateQueueRequest(LOG_CHANGES_QUEUE));
        LOGGER.info("Sending mocked orders...");
        List<Order> mockOrderList = mapJsonMockFileFromProperties(MOCK_FILENAME);
        orderSenderService.sendOrders(mockOrderList);
    }

    public List<Order> mapJsonMockFileFromProperties(String filename) {
        try {
            ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(RESOURCES_PATH + filename)));
            return new ObjectMapper().readValue(inputStream.getInputStream(), new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
