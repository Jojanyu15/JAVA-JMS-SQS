package co.epam.jmstask1orderlogservice.services.jms;

import java.io.IOException;

public interface OrderPublisherService {
    void publishToS3(String orderStatus,String timestamp) throws IOException;
}
