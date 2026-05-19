package multihreading_cons;

import java.lang.reflect.Method;

public class SyncronizedBlockNClass {

    public static void main(String[] args) {
        SyncronizedBlock block1 = new SyncronizedBlock();
    
        Thread t1 = new Thread(() -> {
            block1.m1();
        });
    
        Thread t2 = new Thread(() -> {
            block1.m2();
        });
        
        t1.start();
        t2.start();
        // output: One thread waits untill method is completely executed.
        // Method 1 execution started
        // Method 1 executed
        // Method 2 execution started
        // Method 2 executed

        
        // Thread execution on different object lock syncronized methods.
        SyncronizedMulitBlock mulitBlock = new SyncronizedMulitBlock();
        
        Thread t3 = new Thread(() -> {
            mulitBlock.m1();
        });
        
        Thread t4 = new Thread(() -> {
            mulitBlock.m2();
        });
        
        try {
            Thread.sleep(5000);
        } catch (Exception e) {}

        System.out.println("starting t3 and t4 thread");
        t3.start();
        t4.start();
        // output: 
        // Method 1 execution started
        // Method 2 execution started
        // Method 1 executed
        // Method 2 executed

    }
    
}

// Syncronize block: this class uses self class object, hence methods are locked with single object.
// Even calling with different thread, if one thread is not completly executing one method other thread will wait.
class SyncronizedBlock {

    void m1() {
        synchronized(this) {
            System.out.println("Method 1 execution started");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {}
            System.out.println("Method 1 executed");
        }
    }

    void m2() {
        synchronized(this) {
            System.out.println("Method 2 execution started");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {}
            System.out.println("Method 2 executed");
        }
    }
}

// Locking based on differnt class object.
// If another thread one to execute m2() and m1() is already in wait phase, its allowed here.
class SyncronizedMulitBlock {
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    void m1() {
        synchronized(lock1) {
            System.out.println("Method 1 execution started");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {}
            System.out.println("Method 1 executed");
        }
    }

    void m2() {
        synchronized(lock2) {
            System.out.println("Method 2 execution started");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {}
            System.out.println("Method 2 executed");
        }
    }
}

// Class lock can be add for both static and none static method.
// Object lock can be added for only none static method.
class ClassAndObjectLock {
    static void m1() {
        synchronized(ClassAndObjectLock.class) {
            System.out.println("Method 1 execution started");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {}
            System.out.println("Method 1 executed");
        }
    }

    void m2() {
        synchronized(this) {
            System.out.println("Method 2 execution started");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {}
            System.out.println("Method 2 executed");
        }
    }
}
