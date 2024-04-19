package com.example.Tzms.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@Component
public class MeasureTimeTransactionAspect {

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void registerTransactionSyncrhonization() {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            private long startTime;

            @Override
            public void beforeCommit(boolean readOnly) {
                startTime = System.nanoTime();
            }

            @Override
            public void afterCommit() {
                long endTime = System.nanoTime();
                long transactionTimeNano = endTime - startTime;
                long transactionTimeMillis = transactionTimeNano / 1_000_000;
                System.out.println("Transaction time: " + transactionTimeMillis + " milliseconds.");
            }
        });
    }

}
