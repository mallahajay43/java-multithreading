package advanced;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockExample {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            if (lock.tryLock()) {
                try {
                    System.out.println("Lock auuired, thred: " + Thread.currentThread().getName());
                } finally {
                    lock.unlock();
                }
            }
            else {
                System.out.println("Lock alread aquired, informer thread: " + Thread.currentThread().getName());
            }
        });

        Thread t2 = new Thread(() -> {
            if (lock.tryLock()) {
                try {
                    System.out.println("Lock auuired, thred: " + Thread.currentThread().getName());
                } finally {
                    lock.unlock();
                }
            }
            else {
                System.out.println("Lock alread aquired, informer thread: " + Thread.currentThread().getName());
            }
        });
        Thread t3 = new Thread(() -> {
            if (lock.tryLock()) {
                try {
                    System.out.println("Lock auuired, thred: " + Thread.currentThread().getName());
                } finally {
                    lock.unlock();
                }
            }
            else {
                System.out.println("Lock alread aquired, informer thread: " + Thread.currentThread().getName());
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }    
}

