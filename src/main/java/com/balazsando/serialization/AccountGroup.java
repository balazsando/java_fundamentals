package com.balazsando.serialization;

import com.balazsando.reflection.BankAccount;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class AccountGroup implements Serializable {
    private Map<String, BankAccount> accountMap = new HashMap<>();
    private transient int totalBalance;

    public int getTotalBalance() {
        return totalBalance;
    }

    public void addAccount(BankAccount bankAccount) {
        totalBalance += bankAccount.getBalance();
        accountMap.put(bankAccount.getId(), bankAccount);
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        for (BankAccount account : accountMap.values()) {
            totalBalance += account.getBalance();
        }
    }

    public static void main(String[] args) {
        BankAccount account1 = new BankAccount("John Doe", 5000);
        BankAccount account2 = new BankAccount("Jane Doe", 5000);
        AccountGroup accountGroup = new AccountGroup();
        accountGroup.addAccount(account1);
        accountGroup.addAccount(account2);
        savegroup(accountGroup, "group.dat");
        System.out.println(readgroup("group.dat").getTotalBalance());
    }

    private static void savegroup(AccountGroup group, String fileName) {
        try (ObjectOutputStream os = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            os.writeObject(group);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static AccountGroup readgroup(String fileName) {
        AccountGroup group = null;
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            group = (AccountGroup) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return group;
    }
}
