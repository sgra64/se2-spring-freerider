package de.bht_berlin.freerider;

import org.springframework.stereotype.Component;

/**
 * {@code @Component} class that provides calculation methods. Class is
 * instantiated as a <i>singleton</i> <i>"Spring Bean"</i> object by the\
 * <i>Spring Boot</i> framework.
 */
@Component
public class Calculator {

    int sum(int x, int y) {
        return x + y;
    }
}
