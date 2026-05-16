public class ThreadStates {

    public static void main(String[] args) {

        Thread mainThread = Thread.currentThread();
        
        Thread thread = new Thread(() -> {
            // Printing current thread state: RUNNABLE.
            System.out.println(Thread.currentThread().getName() + " thread is in " + Thread.currentThread().getState() + " state.");
            // Printing main thread state : TIMED_WAITING.
            System.out.println("main thread state: " + mainThread.getState());
        });

        // current state of thread: NEW.
        System.out.println(thread.getName() + " thread is in " + thread.getState() + " state.");

        
        try {
            thread.start();
            // Calling sleep with put main thread in TIMED_WAITING sate.
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // TODO: handle exception
        }

        // current state of thread: TERMINATED.
        System.out.println(thread.getName() + " thread is in " + thread.getState() + " state.");
    }
    
}


// Thread has following states
/**
 * NEW - Thread is created but not started yet
 *   Thread comes is this state when Thread object is created.
 * RUNNABLE - Thread is ready to run or currently running
 *   When we call start() method, thread comes in this state.
 * BLOCKED - Thread is blocked waiting for a monitor lock
 *   When resources are locked, user input dependency, different thread locks.
 * WAITING - Thread is waiting indefinitely for another thread to perform an action
 *   Calling wait() method, puts thread in this state.
 * TIMED_WAITING - Thread is waiting for another thread with a specified time limit
 *   Calling Sleep method puts thread in this sate.
 * TERMINATED - Thread has completed execution
 *   When run method execution is completes thread comes int this state.
 */