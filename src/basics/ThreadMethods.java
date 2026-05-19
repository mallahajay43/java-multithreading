package basics;

/**
 * Thread.sleep(milisecond) 
 *   pauses the currently executing thread for a specified amount of time.
 * 
 * t1.join() 
 *   makes one thread wait until another thread completes its execution.
 * t1.join(miliseond)
 *   maked one thread to wait for given time and the start its execution.
 * 
 * Thread.yield()
 *   temporarily pauses the current thread and gives other threads a chance to execute.
 * 
 * t1.interrupt()
 *   this send interruption signal and set interrupt flag to TRUE and throws intterupted exeption.
 * 
 * t1.isAlive()
 *   check if thread is alive
 *   RUNNABLE -> TERMINATED
 * 
 * t1.setPriority(), t1.getPriority()
 *   sets Priotity of the thread, MIN_PRIORITY = 1, MAX_PRIORITY = 10, NORM_PRIORITY = 5.
 * 
 * t1.setDaemon(true)
 *   mark thread as daemon thread, 
 *   this thread runs in the background and serves to the user threads and dies when user thread terminates.
 * 
 */
public class ThreadMethods {
    public static void main(String[] args) throws InterruptedException {

        Thread daemon = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                System.out.println("Daemon thread is running ...");
            }
        });
        // It gets terminated (even run is not completly executed) if all user threads are terminated.
        daemon.setDaemon(true);
        daemon.start();

        Thread t1 = new Thread(() -> {
            try {
                // Putting thread to TIMED_WAITING state usinig sleep().
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
            // Modifying current thread name using setName method.
            Thread.currentThread().setName("First thread");
            System.out.println("Thread name: " + Thread.currentThread().getName());
        });

        Thread t2 = new Thread(() -> {
            System.out.println("First print");
            // It will temporarily pause the execution and wait for other thread execution having same priority. 
            Thread.yield();
            System.out.println("Second print");
        });

        Thread t3 = new Thread(() -> {
            // while loop runs untill interruped signal is not received.
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Not interrupted...");
            }
        });

        t1.start();
        t2.start();
        t3.start();

        // After 0.2 second t3 thread will be interrupted.
        Thread.sleep(200);
        t3.interrupt();

    }
}


