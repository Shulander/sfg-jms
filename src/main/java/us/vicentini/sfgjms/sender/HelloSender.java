package us.vicentini.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.vicentini.sfgjms.config.JmsConfig;
import us.vicentini.sfgjms.model.HelloWorldMessage;

import javax.jms.Message;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;


    @Scheduled(fixedRate = 10000, initialDelay = 5000)
    public void sendMessage() {
        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_HELLO_WORLD, message);
        log.info("Message Sent!");
    }


    @SneakyThrows
    @Scheduled(fixedRate = 10000)
    public void sendAndReceiveMessage() {
        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        Message responseMessage = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, session -> {
            try {
                Message helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                helloMessage.setStringProperty("_type", "us.vicentini.sfgjms.model.HelloWorldMessage");
                return helloMessage;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });
        String body = responseMessage.getBody(String.class);
        HelloWorldMessage received = objectMapper.readValue(body, HelloWorldMessage.class);
        log.info("Sender Message received: '{}'", received.getMessage());
    }
}
