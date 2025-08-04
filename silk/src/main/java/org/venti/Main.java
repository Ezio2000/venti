package org.venti;

import org.venti.core.engine.Engine;
import org.venti.elastic.output.ElasticOutputPlugin;
import org.venti.kafka.input.KafkaInputPlugin;
import org.venti.ruqi.filter.RuqiLogFilterPlugin;
import org.venti.simple.filter.SimpleFilterPlugin;

import java.util.concurrent.locks.LockSupport;

public class Main {

    public static void main(String[] args) {
        var engine = new Engine()
                .addInputPlugin(new KafkaInputPlugin())
//                .addFilterPlugin(new SimpleFilterPlugin())
                .addFilterPlugin(new RuqiLogFilterPlugin())
                .addOutputPlugin(new ElasticOutputPlugin());
        engine.init();
        engine.start();
        LockSupport.park();
    }

}