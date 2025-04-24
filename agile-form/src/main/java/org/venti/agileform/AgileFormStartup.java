package org.venti.agileform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"org.venti.agileform"})
class AgileFormStartup {

    public static void main(String[] args) {
        SpringApplication.run(AgileFormStartup.class, args);
    }

}
