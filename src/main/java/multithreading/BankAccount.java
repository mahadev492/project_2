package multithreading;

class BankAccount {
    private int balance = 1000;

    // Synchronized method to prevent race condition
    public synchronized void withdraw(int amount, String threadName) {
        if (balance >= amount) {
            System.out.println(threadName + " is withdrawing " + amount);
            balance -= amount;
            System.out.println(threadName + " completed withdrawal. Remaining balance: " + balance);
        } else {
            System.out.println(threadName + " tried to withdraw " + amount + " but insufficient balance!");
        }
    }

    public int getBalance() {
        return balance;
    }
}

class UserThread extends Thread {
    private BankAccount account;
    private int amount;

    UserThread(BankAccount account, int amount, String name) {
        super(name);
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.withdraw(amount, getName());
    }
}


