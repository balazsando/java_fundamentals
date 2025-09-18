package com.balazsando.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionDemo {
    public static void main(String[] args) throws Exception {
        BankAccount acc1 = new BankAccount("4356");
        startWork("com.balazsando.reflection.ReflectionDemo.AccountWorker", acc1);

    }

    private static void startWork(String workerTypeName, Object workerTarget) throws Exception {
        Class<?> workerType = Class.forName(workerTypeName);
        Class<?> targetType = workerTarget.getClass();
        Constructor c = workerType.getConstructor(targetType);
        Object worker = c.newInstance(workerTarget);
        Method doWork = targetType.getMethod("doWork");
        doWork.invoke(worker);

    }

    class AccountWorker implements Runnable {
        BankAccount bankAccount;
        HighVolumeAccount highVolumeAccount;

        public AccountWorker(BankAccount bankAccount) {
            this.bankAccount = bankAccount;
        }

        public AccountWorker(HighVolumeAccount highVolumeAccount) {
            this.highVolumeAccount = highVolumeAccount;
        }

        public void doWork() {
            Thread thread = new Thread(highVolumeAccount != null ? highVolumeAccount : this);
            thread.start();
        }

        @Override
        public void run() {
            char txType = 'd';
            int amount = 100;
            if(txType == 'd') {
                bankAccount.deposit(amount);
            } else {
                bankAccount.withdraw(amount);
            }
        }
    }
}
