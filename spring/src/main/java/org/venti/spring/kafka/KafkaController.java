package org.venti.spring.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("/kafka/send")
    public void send(@RequestParam(name = "msg") String msg) {
        kafkaProducer.sendMessage(msg);
    }

}
