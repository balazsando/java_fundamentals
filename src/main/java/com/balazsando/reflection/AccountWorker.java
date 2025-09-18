package com.balazsando.reflection;

import com.balazsando.metadata.ProcessedBy;
import com.balazsando.metadata.WorkHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WorkHandler(false)
public class AccountWorker implements Runnable, TaskWorker {
    BankAccount bankAccount;
    static ExecutorService pool = Executors.newFixedThreadPool(5);

    @Override
    public void setTarget(Object target) throws IllegalAccessException {
        if (BankAccount.class.isInstance(target)) {
            bankAccount = (BankAccount) target;
        } else {
            throw new IllegalAccessException("Target object is not of type BankAccount");
        }
    }

    @Override
    public void doWork() {
        Thread thread = new Thread(
                HighVolumeAccount.class.isInstance(bankAccount) ? (HighVolumeAccount) bankAccount : this
        );
        thread.start();
    }

    @Override
    public void run() {
        char txType = 'd';
        int amount = 100;
        if (txType == 'd') {
            bankAccount.deposit(amount);
        } else {
            bankAccount.withdraw(amount);
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount("1234");
        startWork(account);
    }

    private static void startWork(Object target) {
        try {
            ProcessedBy processedBy = target.getClass().getAnnotation(ProcessedBy.class);
            Class<?> workerType = processedBy.value();
            TaskWorker worker = (TaskWorker) workerType.newInstance();
            worker.setTarget(target);
            WorkHandler wh = workerType.getAnnotation(WorkHandler.class);
            if (wh == null) {
                throw new IllegalArgumentException("Worker type is not annotated with @WorkHandler");
            }
            if (wh.value()) {
                pool.submit(worker::doWork);
            } else {
                worker.doWork();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

interface TaskWorker {
    void setTarget(Object target) throws IllegalAccessException;

    void doWork();
}
