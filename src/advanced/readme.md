# Java Locks and Synchronization Utilities

Java provides advanced synchronization mechanisms in the `java.util.concurrent.locks` package for better control over multithreading.

Main synchronization utilities:

1. ReentrantLock
2. ReadWriteLock
3. StampedLock
4. Semaphore

---

# 1. ReentrantLock

## Definition

`ReentrantLock` is an advanced alternative to the `synchronized` keyword.

It provides:
- Manual lock control
- Fairness policy
- tryLock()
- interruptible locking
- multiple condition variables

A thread that already holds the lock can acquire it again.  
This is called **reentrant behavior**.

---

# Features

- Explicit locking and unlocking
- Better flexibility than `synchronized`
- Supports fairness
- Supports interruptible lock waiting
- Supports timed lock waiting

---

# Example

```java
import java.util.concurrent.locks.ReentrantLock;

class Counter {

    private int count = 0;

    ReentrantLock lock = new ReentrantLock();

    public void increment() {

        lock.lock();

        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return count;
    }
}

public class ReentrantLockExample {

    public static void main(String[] args) throws Exception {

        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(counter.getCount());
    }
}
```

---

# Important Methods

| Method | Purpose |
|---|---|
| `lock()` | Acquire lock |
| `unlock()` | Release lock |
| `tryLock()` | Try acquiring lock without waiting |
| `lockInterruptibly()` | Acquire lock with interruption support |
| `isLocked()` | Check lock status |

---

# Advantages

- More flexible than `synchronized`
- Better thread control
- Supports fairness policy
- Prevents deadlock using `tryLock()`

---

# 2. ReadWriteLock

## Definition

`ReadWriteLock` allows:
- Multiple threads to read simultaneously
- Only one thread to write at a time

Useful when:
- Reads are frequent
- Writes are rare

---

# Types of Locks

| Lock Type | Access |
|---|---|
| Read Lock | Multiple threads allowed |
| Write Lock | Only one thread allowed |

---

# Example

```java
import java.util.concurrent.locks.ReentrantReadWriteLock;

class SharedData {

    private int value = 0;

    ReentrantReadWriteLock lock =
            new ReentrantReadWriteLock();

    public void read() {

        lock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName()
                    + " reads: " + value);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void write(int newValue) {

        lock.writeLock().lock();

        try {
            value = newValue;

            System.out.println(Thread.currentThread().getName()
                    + " writes: " + value);

        } finally {
            lock.writeLock().unlock();
        }
    }
}
```

---

# Advantages

- Better performance for read-heavy applications
- Multiple readers allowed simultaneously
- Reduces contention

---

# Use Cases

- Cache systems
- Configuration data
- Read-heavy databases

---

# 3. StampedLock

## Definition

`StampedLock` is an advanced lock introduced in Java 8.

It provides:
- Read lock
- Write lock
- Optimistic read lock

It improves performance compared to `ReadWriteLock`.

---

# Lock Modes

| Mode | Description |
|---|---|
| Read Lock | Multiple readers allowed |
| Write Lock | Exclusive writer |
| Optimistic Read | Non-blocking read |

---

# Example

```java
import java.util.concurrent.locks.StampedLock;

class SharedResource {

    private int data = 10;

    private final StampedLock lock =
            new StampedLock();

    public void write(int value) {

        long stamp = lock.writeLock();

        try {
            data = value;
            System.out.println("Written: " + data);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public void read() {

        long stamp = lock.tryOptimisticRead();

        int currentData = data;

        if(!lock.validate(stamp)) {

            stamp = lock.readLock();

            try {
                currentData = data;
            } finally {
                lock.unlockRead(stamp);
            }
        }

        System.out.println("Read: " + currentData);
    }
}
```

---

# Optimistic Read

In optimistic reading:
- No actual lock is taken initially
- Improves performance
- Validation checks whether data changed

---

# Advantages

- Very high read performance
- Better than ReadWriteLock in many cases
- Optimistic reading reduces blocking

---

# Limitations

- Not reentrant
- More complex
- Must carefully manage stamps

---

# 4. Semaphore

## Definition

`Semaphore` controls access to a shared resource using permits.

It limits the number of threads that can access a resource simultaneously.

---

# Real-Life Example

A parking lot with 3 parking spaces:
- Only 3 cars can enter simultaneously
- Others must wait

---

# Example

```java
import java.util.concurrent.Semaphore;

class SharedPrinter {

    Semaphore semaphore = new Semaphore(2);

    public void print(String name) {

        try {

            semaphore.acquire();

            System.out.println(name + " is printing");

            Thread.sleep(2000);

            System.out.println(name + " finished printing");

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}

public class SemaphoreExample {

    public static void main(String[] args) {

        SharedPrinter printer = new SharedPrinter();

        Runnable task = () -> {
            printer.print(Thread.currentThread().getName());
        };

        for(int i = 1; i <= 5; i++) {
            new Thread(task, "Thread-" + i).start();
        }
    }
}
```

---

# Important Methods

| Method | Purpose |
|---|---|
| `acquire()` | Get permit |
| `release()` | Return permit |
| `tryAcquire()` | Try getting permit |
| `availablePermits()` | Check available permits |

---

# Types of Semaphore

| Type | Description |
|---|---|
| Binary Semaphore | Only 1 permit |
| Counting Semaphore | Multiple permits |

---

# Use Cases

- Database connection pools
- Thread pools
- Resource management
- Rate limiting

---

# Comparison Table

| Feature | ReentrantLock | ReadWriteLock | StampedLock | Semaphore |
|---|---|---|---|---|
| Reentrant | Yes | Yes | No | N/A |
| Multiple Readers | No | Yes | Yes | Depends |
| Multiple Writers | No | No | No | Depends |
| Optimistic Read | No | No | Yes | No |
| Permit Based | No | No | No | Yes |
| Fairness Support | Yes | Yes | No | Yes |

---

# Summary

## ReentrantLock
- Advanced replacement for `synchronized`
- Explicit lock handling

## ReadWriteLock
- Multiple readers allowed
- Single writer allowed

## StampedLock
- Faster read operations
- Supports optimistic locking

## Semaphore
- Controls limited resource access
- Uses permits

---