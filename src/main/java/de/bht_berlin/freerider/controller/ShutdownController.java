package de.bht_berlin.freerider.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST-Controller for the '/shutdown' endpoint to shut down the HTTP-Server
 * and the {@link SpringApplication}.
 * 
 * References:
 * - https://stackoverflow.com/questions/26547532/how-to-shutdown-a-spring-boot-application-in-a-correct-way
 */
@RestController
@RequestMapping("/shutdown")
class ShutdownController implements ApplicationListener<ContextClosedEvent> {

    final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private ApplicationContext context;


    @PostMapping("")
    public void shutdown() {
        log.info("Shutting down HTTP server");
        ((ConfigurableApplicationContext) context).close();
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("HTTP server shut down");
    }
}
