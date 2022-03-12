package com.urise.webapp;

public class MainDeadlock {

    public static void main(String[] args) {
        deadLock();
    }

    private static void deadLock() {
        final String bankAccount1 = "Карта пользователя Иванов И.И.";
        final String bankAccount2 = "Карта пользователя Сергеев С.С.";

        new Thread(() -> {
            try {
                transferMoney(bankAccount1, bankAccount2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                transferMoney(bankAccount2, bankAccount1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void transferMoney(String bankAccountFrom, String bankAccountTo) throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        synchronized (bankAccountFrom) {
            System.out.println("Заблокирована " + bankAccountFrom + " транзакцией " + threadName);
            bankAccountFrom.wait();
            synchronized (bankAccountTo) {
                System.out.println("Заблокирована" + bankAccountTo + " транзакцией " + threadName);
            }
            System.out.println("Заблокирована " + bankAccountTo + " транзакцией " + threadName);
        }
        System.out.println("Заблокирована " + bankAccountFrom + " транзакцией " + threadName);
    }
}
