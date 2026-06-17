package de.bht_berlin.freerider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The {@link CommandLineRunner} interface is a way in <i>Spring Boot</i>
 * to launch application code.
 * 
 * <i>Spring Boot</i> finds classes implementing the {@link CommandLineRunner}
 * interface during the initial class-scan. If the class carries a
 * {@code @Component} annotation, <i>Spring Boot</i> creates a <i>"Bean"</i>
 * object of this class and invokes the {@code run(String... args)}-method.
 * 
 * Multiple {@link CommandLineRunner} classes may exist and are being invoked
 * in random order.
 * 
 * The principle of not invoking {@code run(String... args)} directly from
 * own code, but <i>"being invoked"</i> by the framework is called
 * <i>"Inversion-of-Control (IoC)"</i>.
 */
@Component
public class DemoCommandLineRunner implements CommandLineRunner {

    /**
     * Definition of a variable of a <i>Bean</i> object that is automatically
     * initialized by the <i>Spring Boot</i> framework with {@code Autowired}.
     * 
     * The principle is called <i>"Dependency Injection (DI)"</i>.
     */
    @Autowired
    Calculator calculator;

    /**
     * Constructor. Dependencies can also be injected by the constructor of a
     * <i>Bean</i> class, which is called: <i>"Constructor Injection"</i>.
     * @param calculator
     */
    DemoCommandLineRunner(Calculator calculator) {
        this.calculator = calculator;
        // 
        System.out.println("Bean (object) created for @Component class: DemoCommandLineRunner");
    }

    /**
     * Method implementing the {@link CommandLineRunner} interface.
     */
    @Override
    public void run(String... args) throws Exception {

        /*
         * Variable {@code calculator} can be used after dependency injection.
         */
        int result = calculator.sum(10, 12);
        // 
        System.out.println(String.format("%sDemoCommandLineRunner, result: %d", "\\\\\n", result));
    }
}
