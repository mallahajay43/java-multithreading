# Thread in Java

## What is a Thread?

A **Thread** is the smallest unit of execution within a process.  
Java allows multiple threads to run concurrently, which helps in performing multiple tasks at the same time.

Example:
- Downloading a file while playing music
- Running background tasks in a web application

Java provides built-in support for multithreading through the `Thread` class and `Runnable` interface.

---

# Ways to Create a Thread in Java

## 1. By Extending the Thread Class

```java
class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("Thread is running...");
    }

    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
    }
}
```

### Explanation
- `run()` contains the task performed by the thread.
- `start()` creates a new thread and internally calls `run()`.

---

## 2. By Implementing Runnable Interface

```java
class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("Runnable thread is running...");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new MyRunnable());
        thread.start();
    }
}
```

### Why Runnable is Preferred?
- Java supports single inheritance only.
- A class can implement multiple interfaces.
- Better separation of task and thread logic.

---

# Thread Methods

| Method | Description |
|---|---|
| `start()` | Starts a new thread |
| `run()` | Contains thread logic |
| `sleep(ms)` | Pauses thread for given milliseconds |
| `join()` | Waits for thread completion |
| `isAlive()` | Checks whether thread is running |

---

# Thread Life Cycle in Java

A thread passes through different states during execution.

## 1. NEW
- Thread object is created.
- Thread has not started yet.

```java
Thread t = new Thread();
```

---

## 2. RUNNABLE
- Thread is ready to run.
- After calling `start()`, thread enters runnable state.

```java
t.start();
```

---

## 3. RUNNING
- Thread scheduler picks the thread for execution.
- `run()` method executes.

---

## 4. BLOCKED / WAITING / TIMED_WAITING
Thread temporarily stops execution due to:
- `sleep()`
- `wait()`
- waiting for lock/resource
- `join()`

Example:

```java
Thread.sleep(1000);
```

---

## 5. TERMINATED
- Thread finishes execution.
- `run()` method completes.

---

# Thread Life Cycle Diagram

```text
NEW
  |
start()
  v
RUNNABLE
  |
CPU Scheduler
  v
RUNNING
  |
  |---- sleep()/wait()/join()
  |           |
  |           v
  |      WAITING/BLOCKED
  |           |
  |-----------|
  |
run() completed
  v
TERMINATED
```

---

# Example of Thread Life Cycle

```java
class DemoThread extends Thread {

    @Override
    public void run() {
        try {
            System.out.println("Thread is running");
            Thread.sleep(2000);
            System.out.println("Thread finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        DemoThread t = new DemoThread();

        System.out.println(t.getState()); // NEW

        t.start();

        System.out.println(t.getState()); // RUNNABLE

    }
}
```

---

# Advantages of Multithreading

- Better CPU utilization
- Concurrent task execution
- Improved application performance
- Responsive user interface

---

# Disadvantages of Multithreading

- Complex debugging
- Synchronization issues
- Deadlock possibility
- Context switching overhead

---

# Conclusion

- A thread is an independent path of execution.
- Threads enable concurrent programming in Java.
- Threads can be created using:
  - `Thread` class
  - `Runnable` interface
- A thread goes through multiple life cycle states:
  `NEW → RUNNABLE → RUNNING → WAITING/BLOCKED → TERMINATED`
