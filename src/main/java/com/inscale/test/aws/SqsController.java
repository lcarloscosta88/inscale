package com.inscale.test.aws;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inscale.test.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SqsController {

    @Value("${spring.aws.queueUrl}")
    private String queue;

    private final AmazonSQSAsync amazonSQSAsync;

    @Autowired
    public SqsController(AmazonSQSAsync amazonSQSAsync) { this.amazonSQSAsync = amazonSQSAsync;}

    public String sendMessage(PersonDTO personDTO) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(personDTO);
        SendMessageRequest messageRequest = new SendMessageRequest().withQueueUrl(queue).withMessageBody(json);
        return amazonSQSAsync.sendMessage(messageRequest).getMessageId();
    }
}
