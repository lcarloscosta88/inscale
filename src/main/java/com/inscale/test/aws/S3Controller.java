package com.inscale.test.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inscale.test.dto.PersonDTO;
import com.inscale.test.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;

@Service
public class S3Controller {

    @Value("${spring.aws.bucketName}")
    private String bucket;
    private final AmazonS3 amazonS3;

    @Autowired
    public S3Controller(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public ResponseEntity<String> uploadToS3(Person person) {
        try {
            String filename = person.getName() + LocalDate.now() + ".inc";

            InputStream personData = convertToInputStream(person);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("application/octet-stream");

            amazonS3.putObject(new PutObjectRequest(bucket, filename, personData, metadata));

            return ResponseEntity.ok("Person data uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading person data: " + e.getMessage());
        }
    }

    private InputStream convertToInputStream(Person personDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] jsonBytes = objectMapper.writeValueAsBytes(personDTO);
            return new ByteArrayInputStream(jsonBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

