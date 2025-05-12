package org.venti.agileform.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.venti.jdbc.api.Jdbc;
import org.venti.jdbc.plugin.transaction.Transaction;
import org.venti.jdbc.plugin.transaction.TransactionJdbcImpl;

@Aspect
@Component
public class TransactionAspect {

    @Autowired
    public Jdbc jdbc;

    @Pointcut("@annotation(org.venti.agileform.anno.TransactionMethod)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        if (jdbc instanceof TransactionJdbcImpl transactionJdbc) {
            try (var innerJdbc = transactionJdbc.transaction()) {
                Transaction.begin(innerJdbc);
                var result = pjp.proceed();
                transactionJdbc.commit();
                return result;
            } finally {
                Transaction.clear();
            }
        }
        return pjp.proceed();
    }

}
