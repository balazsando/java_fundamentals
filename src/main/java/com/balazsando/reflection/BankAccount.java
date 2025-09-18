package com.balazsando.reflection;

import com.balazsando.metadata.ProcessedBy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@ProcessedBy(AccountWorker.class)
public class BankAccount implements Serializable {
    private static final long serialVersionUID = 6841347932655341248L;
    protected String id;
    protected int balance;
    private char lastTxType;
    private int lastTxAmount;

    public BankAccount(String id) {
        this.id = id;
    }

    public BankAccount(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public synchronized int getBalance() {
        return balance;
    }

    public synchronized void deposit(int amount) {
        this.balance += amount;
        lastTxType = 'D';
        lastTxAmount = amount;
    }

    public synchronized void withdraw(int amount) {
        this.balance -= amount;
        lastTxType = 'W';
        lastTxAmount = amount;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        BankAccount bankAccount = new BankAccount("1234");
        bankAccount.deposit(250);
        saveAccount(bankAccount, "account.dat");
        System.out.println(loadAccount("account.dat"));
        doWork(bankAccount);
//        Class<?> c = BankAccount.class;
        Class<?> c = Class.forName("com.balazsando.reflection.BankAccount");
        showname(c);
    }

    private static BankAccount loadAccount(String filename){
        BankAccount bankAccount = null;
        try(ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(filename)))) {
            bankAccount = (BankAccount) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return bankAccount;
    }

    private static void saveAccount(BankAccount bankAccount, String fileName) {
        try(ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            bankAccount.writeObject(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); //redundant it's just for showcase
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField fields = in.readFields();
        id = (String) fields.get("id", null);
        balance = fields.get("balance", 0);
        lastTxType = fields.get("lastTxType", 'u');
        lastTxAmount = fields.get("lastTxAmount", -1);
    }

    public static void showname(Class<?> theClass) {
        System.out.println("Class: " + theClass.getSimpleName());
    }

    public static void doWork(Object obj) {
        Class<?> clazz = obj.getClass();
        showname(clazz);
    }
}
