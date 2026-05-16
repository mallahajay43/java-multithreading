public class CreateThread {
    
    public static void main(String[] args) {
        // Creating object of the custom thread class.
        ExtendedThread extThread = new ExtendedThread();

        // Creating thread by passing Runnable object or Custom Runnable inheritted class object.
        Thread thread1 = new Thread(new RunnableThread());

        // Creating thread by Runnable funtional interface.
        Runnable task = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + " thread is in " + Thread.currentThread().getState() + " state. runnable functional interface.");
            }
        };
        Thread thread2 = new Thread(task);

        // Using lamda function to create thread.
        Thread lamdaThread = new Thread(() -> {
            Thread.currentThread().setName("lamda_thread");
            System.out.println(Thread.currentThread().getName() + " thread is in " + Thread.currentThread().getState() + " state");
        });
        
        // Calling start method.
        extThread.start();
        thread1.start();
        thread2.start();
        lamdaThread.start();
    }
}

// Thread creation by extending Thread class.
class ExtendedThread extends Thread {
    @Override
    public void run() {
        System.out.println("Current thread name: " + Thread.currentThread().getName());
        System.out.println("Current state: " + Thread.currentThread().getState());
    }

    ExtendedThread() {
        // This will run on main thread, as at this time thread is not created.
        System.out.println(currentThread().getName() + " thread is in " + currentThread().getState() + " state");
    }
}

// Thread creation by implementing Runnable interface.
class RunnableThread implements Runnable {

    @Override
    public void run() {
        System.out.println("Runnable thread name: " + Thread.currentThread().getName() + ", state: " + Thread.currentThread().getState());
    }

}


/**
 * Note**
 * Calling start() method more than one for single thread causes IllegalThreadStateException.
 * This can be handeled by Thread pool with the help Executors and Executor service.
 */