# Various Thread Methods in Java

Java provides several useful methods in the `Thread` class to control thread execution and behavior.

---

# 1. sleep()

## Definition
`sleep()` pauses the currently executing thread for a specified amount of time.

## Syntax

```java
Thread.sleep(milliseconds);
```

## Example

```java
class SleepExample {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Start");

        Thread.sleep(2000);

        System.out.println("End after 2 seconds");
    }
}
```

## Important Points
- Causes the thread to enter `TIMED_WAITING` state.
- Does not release locks.
- Throws `InterruptedException`.

---

# 2. join()

## Definition
`join()` makes one thread wait until another thread completes its execution.

## Syntax

```java
thread.join();
```

## Example

```java
class MyThread extends Thread {

    public void run() {
        for(int i = 1; i <= 5; i++) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) throws Exception {

        MyThread t = new MyThread();

        t.start();

        t.join();

        System.out.println("Main thread resumes");
    }
}
```

## Important Points
- Used for thread synchronization.
- Current thread waits until target thread finishes.

---

# 3. yield()

## Definition
`yield()` temporarily pauses the current thread and gives other threads a chance to execute.

## Syntax

```java
Thread.yield();
```

## Example

```java
class YieldExample extends Thread {

    public void run() {

        for(int i = 1; i <= 5; i++) {

            System.out.println(Thread.currentThread().getName());

            Thread.yield();
        }
    }

    public static void main(String[] args) {

        YieldExample t1 = new YieldExample();
        YieldExample t2 = new YieldExample();

        t1.start();
        t2.start();
    }
}
```

## Important Points
- It is only a suggestion to the thread scheduler.
- Scheduler may ignore it.

---

# 4. interrupt()

## Definition
`interrupt()` is used to interrupt a sleeping or waiting thread.

## Syntax

```java
thread.interrupt();
```

## Example

```java
class InterruptExample extends Thread {

    public void run() {

        try {
            Thread.sleep(5000);
            System.out.println("Thread completed");
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }

    public static void main(String[] args) {

        InterruptExample t = new InterruptExample();

        t.start();

        t.interrupt();
    }
}
```

## Important Points
- Does not stop the thread forcefully.
- Sets interrupt flag.
- Sleeping/waiting thread throws `InterruptedException`.

---

# 5. isAlive()

## Definition
`isAlive()` checks whether a thread is still running.

## Syntax

```java
thread.isAlive();
```

## Example

```java
class AliveExample extends Thread {

    public void run() {
        System.out.println("Thread running");
    }

    public static void main(String[] args) {

        AliveExample t = new AliveExample();

        System.out.println(t.isAlive());

        t.start();

        System.out.println(t.isAlive());
    }
}
```

## Important Points
- Returns `true` if thread is active.
- Returns `false` if thread has not started or already terminated.

---

# 6. Thread Priority

## Definition
Priority determines the importance of a thread for scheduling.

## Range
- Minimum Priority = 1
- Normal Priority = 5
- Maximum Priority = 10

## Methods

```java
setPriority(int priority);
getPriority();
```

## Example

```java
class PriorityExample extends Thread {

    public void run() {
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {

        PriorityExample t1 = new PriorityExample();
        PriorityExample t2 = new PriorityExample();

        t1.setPriority(10);
        t2.setPriority(1);

        t1.start();
        t2.start();
    }
}
```

## Important Points
- Higher priority threads may get more CPU time.
- Thread scheduling behavior depends on JVM and OS.

---

# 7. Daemon Thread

## Definition
A daemon thread is a background thread that runs to support user threads.

Examples:
- Garbage Collector
- Background monitoring tasks

## Method

```java
setDaemon(true);
```

## Example

```java
class DaemonExample extends Thread {

    public void run() {

        while(true) {
            System.out.println("Daemon thread running");
        }
    }

    public static void main(String[] args) {

        DaemonExample t = new DaemonExample();

        t.setDaemon(true);

        t.start();

        System.out.println("Main thread finished");
    }
}
```

## Important Points
- JVM exits when all user threads finish.
- Daemon threads terminate automatically.
- Must call `setDaemon(true)` before `start()`.

---

# Summary Table

| Method | Purpose |
|---|---|
| `sleep()` | Pause thread execution |
| `join()` | Wait for another thread |
| `yield()` | Give chance to other threads |
| `interrupt()` | Interrupt sleeping/waiting thread |
| `isAlive()` | Check thread status |
| `setPriority()` | Set thread priority |
| `setDaemon()` | Create background thread |

---