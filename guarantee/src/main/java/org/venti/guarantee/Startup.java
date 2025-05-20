package org.venti.guarantee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"org.venti.guarantee"})
class Startup {

    public static void main(String[] args) {
        SpringApplication.run(Startup.class, args);
    }

}
