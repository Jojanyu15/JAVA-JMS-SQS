package co.epam.jmstask1orderlogservice;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JmsTask1OrderLogServiceApplication implements CommandLineRunner {
    @Autowired
    AmazonS3 amazonS3;
    @Value("${buckets.processed-bucket}")
    private String S3_BUCKET_NAME;

    public static void main(String[] args) {
        SpringApplication.run(JmsTask1OrderLogServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (!amazonS3.doesBucketExistV2(S3_BUCKET_NAME)) {
            amazonS3.createBucket(S3_BUCKET_NAME);
        }

    }
}
