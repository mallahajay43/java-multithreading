package advanced;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    public static void main(String[] args) {
        ReadWrite rw = new ReadWrite(); 
    
        Thread w1 = new Thread(() -> {
            try {
                rw.write(1);
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        });
        Thread w2 = new Thread(() -> {
            try {
                rw.write(2);
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        });
        Thread w3 = new Thread(() -> {
            try {
                rw.write(3);
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        });
    
        Thread r1 = new Thread(() -> {
            try {
                rw.read();
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        });
        Thread r2 = new Thread(() -> {
            try {
                rw.read();
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        });
        Thread r3 = new Thread(() -> {
            try {
                rw.read();
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        });
    
        r1.start();
        w1.start();
        r2.start();
        w2.start();
        r3.start();
        w3.start();
    }
}

class ReadWrite {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    Lock rl = lock.readLock();
    Lock wl = lock.writeLock();

    int value;

    void read() {
        rl.lock();
        try {
            System.out.println("Read operation performed: " + this.value);
        } finally {
            rl.unlock();
        }
    }

    void write(int value) {
        wl.lock();
        try {
            this.value = value;
            System.out.println("Write operation performed: " + this.value);
        } finally {
            wl.unlock();
        }
    }
}
