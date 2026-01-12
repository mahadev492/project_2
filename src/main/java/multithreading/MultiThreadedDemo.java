package multithreading;

public class MultiThreadedDemo {
    public static void main(String[] args) {
        BankAccount sharedAccount = new BankAccount();

        // Creating multiple threads
        Thread t1 = new UserThread(sharedAccount, 700, "User-1");
        Thread t2 = new UserThread(sharedAccount, 500, "User-2");

        t1.start();
        t2.start();
    }
}
