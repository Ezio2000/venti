package org.venti.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

public class SimpleAgent {

//    public static final Logger log = Logger.getLogger(SimpleAgent.class.getName());

    public static final Logger log = LoggerFactory.getLogger(SimpleAgent.class);

    public static void premain(String args, Instrumentation inst) {
        new AgentBuilder.Default()
                .type(ElementMatchers.nameStartsWith("org.venti"))
                .transform((builder, _, _, _, _) -> builder
                        .visit(Advice.to(MethodAdvice.class)
                                .on(ElementMatchers.isMethod())))
                .installOn(inst);
    }

    public static class MethodAdvice {

        @Advice.OnMethodEnter
        public static long enter(@Advice.Origin String methodInfo) {
            return System.currentTimeMillis();
        }

        @Advice.OnMethodExit(onThrowable = Throwable.class)
        public static void exit(@Advice.Enter long startTime,
                                @Advice.Origin String methodInfo,
                                @Advice.Thrown Throwable t) {
            long duration = System.currentTimeMillis() - startTime;
            if (t != null) {
                log.error(STR."\{methodInfo} | Duration: \{duration}ms | Exception: \{t}", t);
            } else {
                log.info(STR."\{methodInfo} | Duration: \{duration}ms");
            }
        }
    }

}