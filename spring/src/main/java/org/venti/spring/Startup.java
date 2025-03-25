package org.venti.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"org.venti"})
class Startup {

    public static void main(String[] args) {
        SpringApplication.run(Startup.class, args);
    }

}
