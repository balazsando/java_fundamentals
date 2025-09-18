package com.balazsando.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

class Worker implements Runnable {
    private final BankAccount account;

    public Worker(BankAccount bankAccount) {
        this.account = bankAccount;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            int startBalance = account.getBalance();
//            synchronized (account) {
            account.deposit(10);
//            }
            int endBalance = account.getBalance();
            System.out.println("Start balance " + startBalance + " end balance " + endBalance);
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        BankAccount account = new BankAccount(100);
        for (int i = 0; i < 5; i++) {
            Worker worker = new Worker(account);
            executorService.submit(worker);
        }

        executorService.shutdown();
    }
}

class TxWorker implements Runnable {
    protected final BankAccount account;
    protected char txType;
    protected int amount;

    public TxWorker(BankAccount account, char txType, int amount) {
        this.account = account;
        this.txType = txType;
        this.amount = amount;
    }

    @Override
    public void run() {
        int startBalance = account.getBalance();
        if (txType == 'w') {
            account.withdrawal(amount);
        } else if (txType == 'd') {
            account.deposit(amount);
        }
        int endBalance = account.getBalance();
        System.out.println("Start balance " + startBalance + " end balance " + endBalance);
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        BankAccount account = new BankAccount(100);

        for (int i = 0; i < 5; i++) {
            TxWorker txWorker = new TxWorker(account, 'w', 10);
            executorService.submit(txWorker);
        }

        for (int i = 0; i < 5; i++) {
            TxWorker txWorker = new TxWorker(account, 'd', 10);
            executorService.submit(txWorker);
        }

        executorService.shutdown();
    }
}

class TxPromotionWorker extends TxWorker {
    public TxPromotionWorker(BankAccount account, char txType, int amount) {
        super(account, txType, amount);
    }

    @Override
    public void run() {
        int startBalance = account.getBalance();
        if (txType == 'w') {
            account.withdrawal(amount);
        } else if (txType == 'd') {
            synchronized (account) {
                account.deposit(amount);
                if (account.getBalance() > 500) {
                    int bonus = (int) ((account.getBalance() - 500) * 0.01);
                    account.deposit(bonus);
                }
            }
        }
        int endBalance = account.getBalance();
        System.out.println("Start balance " + startBalance + " end balance " + endBalance);
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        BankAccount account = new BankAccount(400);

        char[]  type= new char[]{'w', 'd'};
        for (int i = 0; i < 20; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt( 2);
            TxWorker txWorker = new TxPromotionWorker(account, type[randomInt], 100);
            executorService.submit(txWorker);
        }

        executorService.shutdown();
    }
}

public class BankAccount {
    private int balance;

    public BankAccount(int startBalance) {
        balance = startBalance;
    }

    public int getBalance() {
        return balance;
    }

    public synchronized void deposit(int amount) {
        this.balance += amount;
    }

    public synchronized void withdrawal(int amount) {
        this.balance -= amount;
    }
}
