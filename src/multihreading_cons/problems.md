# Problems in Multithreading

In multithreading, multiple threads execute simultaneously and share resources such as variables and objects.  
Without proper synchronization, several issues can occur.

The most common multithreading problems are:

1. Race Condition
2. Visibility Problem
3. Ordering Problem

---

# 1. Race Condition

## Definition

A **Race Condition** occurs when multiple threads access and modify shared data at the same time, and the final result depends on the order of execution of threads.

Since thread execution order is unpredictable, incorrect results may occur.

---

# Example of Race Condition

```java
class Counter {

    int count = 0;

    public void increment() {
        count++;
    }
}

public class RaceConditionExample {

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

        System.out.println(counter.count);
    }
}
```

---

# Expected Output

```text
2000
```

But actual output may be:

```text
1795
1987
1920
```

---

# Why It Happens?

The statement:

```java
count++;
```

is not atomic.

It internally performs:

```text
1. Read count
2. Increment value
3. Write back
```

If two threads execute simultaneously, updates may be lost.

---

# Solution

Use:
- `synchronized`
- `AtomicInteger`
- Locks

## Fixed Example Using synchronized

```java
public synchronized void increment() {
    count++;
}
```

---

# 2. Visibility Problem

## Definition

A **Visibility Problem** occurs when one thread changes a shared variable, but other threads cannot immediately see the updated value.

This happens because threads may cache variables locally.

---

# Example

```java
class SharedData {

    boolean flag = true;

    public void runTask() {

        while(flag) {

        }

        System.out.println("Stopped");
    }
}

public class VisibilityExample {

    public static void main(String[] args) throws Exception {

        SharedData data = new SharedData();

        Thread t1 = new Thread(() -> {
            data.runTask();
        });

        t1.start();

        Thread.sleep(1000);

        data.flag = false;
    }
}
```

---

# Problem

Even after setting:

```java
data.flag = false;
```

the thread may continue running forever.

Reason:
- Thread `t1` may use cached value of `flag`.

---

# Solution

Use `volatile`.

## Fixed Example

```java
volatile boolean flag = true;
```

---

# What volatile Does?

- Ensures updated value is visible to all threads.
- Prevents thread-local caching issues.

---

# 3. Ordering Problem

## Definition

An **Ordering Problem** occurs when instructions execute in a different order than expected due to compiler optimization, CPU optimization, or thread scheduling.

This may cause inconsistent behavior in multithreaded programs.

---

# Example

```java
class Shared {

    int data = 0;
    boolean ready = false;

    public void writer() {
        data = 100;
        ready = true;
    }

    public void reader() {

        if(ready) {
            System.out.println(data);
        }
    }
}
```

---

# Problem

Expected behavior:
- `data` should become `100`
- then `ready` becomes `true`

But due to instruction reordering:

```text
ready = true
data = 100
```

Reader thread may print:

```text
0
```

instead of:

```text
100
```

---

# Why Reordering Happens?

For performance optimization:
- JVM
- CPU
- Compiler

may reorder instructions if single-threaded behavior remains unchanged.

But in multithreading, this can create issues.

---

# Solution

Use:
- `volatile`
- `synchronized`
- Locks

## Fixed Example

```java
volatile boolean ready = false;
```

or synchronize methods.

---

# Difference Between Problems

| Problem | Cause | Result |
|---|---|---|
| Race Condition | Multiple threads modify shared data simultaneously | Incorrect data |
| Visibility Problem | Updated value not visible across threads | Stale data |
| Ordering Problem | Instructions execute in unexpected order | Inconsistent behavior |

---

# Summary

## Race Condition
- Happens during concurrent modification.
- Causes incorrect results.

## Visibility Problem
- Threads cannot see updated values immediately.
- Solved using `volatile`.

## Ordering Problem
- JVM/CPU may reorder instructions.
- Solved using synchronization or `volatile`.

---

# Important Keywords

| Keyword | Purpose |
|---|---|
| `synchronized` | Mutual exclusion and visibility |
| `volatile` | Visibility and ordering guarantee |
| `AtomicInteger` | Atomic operations |
| `Lock` | Advanced thread synchronization |

---