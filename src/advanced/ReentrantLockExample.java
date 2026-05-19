package advanced;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer(); 
    
        Thread w = new Thread(() -> {
            for (int i = 0; i<5 ; i++) {
                try {
                    pc.produce(i);
                    Thread.sleep(200);
                } catch (InterruptedException e) {}
            }
        });
    
        Thread r = new Thread(() -> {
            for (int i = 0; i<5 ; i++) {
                try {
                    pc.consume();
                    Thread.sleep(200);
                } catch (InterruptedException e) {}
            }
        });
    
        r.start();
        w.start();
    }
}
/**
 * Aquiring lock with reentrant lock
 * Advantage
 *  1. Fair Locking
 *      The Issue: In standard synchronized blocks, there is no guarantee which thread gets the lock next.
 *      A "greedy" thread can starve others.
 *      The Fix: You can pass true to the ReentrantLock constructor.
 *      This creates a fair lock where the thread that has been waiting the longest gets priority.
 *    
 *  2. The tryLock()
 *      MethodNon-Blocking: A thread can attempt to acquire a lock without getting stuck.
 *      Decision Making: If the lock is held by someone else, tryLock() returns false immediately. 
 *      Your code can then do something else (like log an error or try a different task) instead of sitting idle.
 *      Timeouts: You can use tryLock(long time, TimeUnit unit) to wait for a lock for a specific duration before giving up.
 * 
 *  3. Multiple Condition 
 *      VariablesPrecision: For example in Producer-Consumer,
 *      you can create multiple Condition objects (notFull, notEmpty) for a single lock.
 *      Efficiency: You can wake up specific groups of threads.
 *      With synchronized, notifyAll() wakes up every waiting thread,
 *      which wastes CPU cycles if only one type of thread (e.g., a Consumer) can actually proceed.
 * 
 *  4. Lock Interruptibility
 *      Responsiveness: If a thread is waiting for a ReentrantLock,
 *      you can interrupt it using lockInterruptibly().
 *      Comparison: A thread stuck waiting for a synchronized block cannot be interrupted;
 *      it will stay stuck until it gets the lock.
 * 
 *  5. Functional Querying
 *      Visibility: You can programmatically check the state of the lock.
 *      Methods: You can call isLocked(), getHoldCount(), or getQueueLength() to see how many threads are currently waiting.
 *      This is great for monitoring and debugging performance bottlenecks.
 * 
 */
class LockDemo {
    Lock lock = new ReentrantLock();

    int value;
    boolean consumed = false;

    void read() {
        lock.lock();
        try {
            System.out.println("Read operation completed value: " + this.value);
        }
        finally {
            lock.unlock();
        }
    }

    void write(int value) {
        lock.lock();
        try {
            this.value = value;
            System.out.println("Write operation completed value: " + this.value);
        }
        finally {
            lock.unlock();
        }
    }
}

/**
 * Reentrnt lock and condition, producer consumer problem.
 */
class ProducerConsumer {
    Lock lock = new ReentrantLock();
    // Creating conditions.
    Condition notConsumed = lock.newCondition();
    Condition notProduced = lock.newCondition();

    int value;
    // Flag value.
    boolean produced = false;

    void produce(int value) throws InterruptedException {
        lock.lock();
        try {
            while (produced) {
                notProduced.await();
            }
            this.value = value;
            produced = true;
            notConsumed.signal();
        } finally {
            lock.unlock();
        }
    }

    void consume() throws InterruptedException {
        lock.lock();
        try {
            while (!produced) {
                notConsumed.await();
            }
            System.out.println("Consumed value: " + this.value);
            produced = false;
            notProduced.signal();
        } finally {
            lock.unlock();
        }
    }
}

