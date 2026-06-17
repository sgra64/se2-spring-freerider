package de.bht_berlin.freerider;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Class demonstrates an {@code @EventListener}.
 */
@Component
public class DemoEventListener {

    /**
     * Constructor.
     */
    private DemoEventListener() {
        System.out.println("Bean (object) created for @Component class: DemoEventListener");
    }

    /**
     * Method is not directly invoked anywhere in the code, it is
     * called upon an <i>event</i> raised after <i>Spring Boot</i>
     * has finished initialization.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("DemoEventListener: ApplicationReadyEvent event received");
    }
}
