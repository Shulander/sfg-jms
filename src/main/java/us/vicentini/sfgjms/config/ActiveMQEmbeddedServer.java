package us.vicentini.sfgjms.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
@Profile("local")
public class ActiveMQEmbeddedServer {

    private final ActiveMQServer server;


    @SneakyThrows
    public ActiveMQEmbeddedServer() {
        log.info("Constructing!!!");
        ConfigurationImpl configuration = new ConfigurationImpl()
                .setPersistenceEnabled(false)
                .setJournalDirectory("target/data/journal")
                .setSecurityEnabled(false)
                .addAcceptorConfiguration("invm", "vm://0");
        server = ActiveMQServers.newActiveMQServer(configuration);
    }


    @SneakyThrows
    @PostConstruct
    public void init() {
        log.info("Server starting!");
        server.start();
    }


    @SneakyThrows
    @PreDestroy
    public void stop() {
        log.info("Server stop!!!");
        server.stop(true);
    }
}
