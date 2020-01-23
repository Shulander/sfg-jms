package us.vicentini.sfgjms.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import us.vicentini.sfgjms.config.JmsConfig;
import us.vicentini.sfgjms.model.HelloWorldMessage;

@Slf4j
@Component
public class HelloListener {

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers, Message message) {
        log.info("I Got a Message!!!");
        log.info(helloWorldMessage.toString());
    }
}
