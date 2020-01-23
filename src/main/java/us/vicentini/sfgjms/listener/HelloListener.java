package us.vicentini.sfgjms.listener;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import us.vicentini.sfgjms.config.JmsConfig;
import us.vicentini.sfgjms.model.HelloWorldMessage;

import javax.jms.Message;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelloListener {

    private final JmsTemplate jmsTemplate;


    @JmsListener(destination = JmsConfig.MY_HELLO_WORLD)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers, Message message) {
        log.info("Listener Message received: " + helloWorldMessage.getMessage());
    }


    @SneakyThrows
    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
    public void listenForHello(@Payload HelloWorldMessage helloWorldMessage,
                               @Headers MessageHeaders headers, Message message) {
        HelloWorldMessage responseWorldMessage = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("World!!")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), responseWorldMessage);

        log.info("Listener Message received:  '{}'", helloWorldMessage.getMessage());
    }
}
