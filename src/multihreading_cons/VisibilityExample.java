package multihreading_cons;

public class VisibilityExample {
    // thread t2 will never terminate.
    // static boolean flag = false;

    // Reoslves the visibility issue.
    static volatile boolean flag = false;
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO: handle exception
            }

            flag = true;
        });

        Thread t2 = new Thread(() -> {
            while (!flag) {
                // 
            }
            System.out.println("Thread 2 executed!");
        });

        t1.start();
        t2.start();
    }
}

/**
 * As each threach has its own cache and during the unsycronized execution it fecthes values from caches and not picks from the RAM, causes a visiblity issue.
 * In the above examaple during the while loop execution it fetching flag value from cache not from the RAM hence it not getting the updated value.
 * 
 * SOLUTION
 *  We need to force thread to pick flag value from RAM (real time updated value, not cached.)
 *  We can use sysncronized block or make flag variable as volatile.
 */
