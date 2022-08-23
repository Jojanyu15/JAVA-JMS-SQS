package co.epam.jmstask1orderlogservice.services.jms;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class OrderStatusPublisherImpl implements OrderPublisherService {

    @Autowired
    AmazonS3 amazonS3;
    @Value("${buckets.processed-bucket}")
    private String s3BucketName;
    @Value("${logs.filename}")
    private String logFilename;
    @Value("${logs.extension}")
    private String logExtension;
    private final String logFileFullName = logFilename + logExtension;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatusPublisherImpl.class);

    @Override
    public void publishToS3(String orderStatus, String timestamp) throws IOException {
        try {
            LOGGER.info(timestamp + "\t" + orderStatus);
            amazonS3.putObject(s3BucketName, logFileFullName, createOrderStatusLog(orderStatus, timestamp));
        } catch (AmazonServiceException e) {
            LOGGER.error(e.getErrorMessage());
        }
    }

    private String createOrderStatusLog(String orderStatus, String timestamp) throws IOException {
        return readS3FileLogs() + timestamp + "\t" + orderStatus + "\n";
    }

    private String readS3FileLogs() throws IOException {
        if (doesLogFileNotExists()) {
            createLogFile();
        }
        InputStream logContent = amazonS3.getObject(s3BucketName, logFileFullName).getObjectContent();
        return logContent == null ? "" : new String(logContent.readAllBytes());
    }

    private void createLogFile() throws IOException {
        Path tempFile = Files.createTempFile(logFilename, logExtension);
        amazonS3.putObject(s3BucketName, logFileFullName, tempFile.toFile());
    }

    private boolean doesLogFileNotExists() {
        return !amazonS3.doesObjectExist(s3BucketName, logFileFullName);
    }
}
