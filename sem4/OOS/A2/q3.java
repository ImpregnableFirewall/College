public class SeriesSum {
    static int currentFactorial;
    static boolean ready = false;
    static final Object lock = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                int fact = 1;
                for (int j = 2; j <= i; j++) fact *= j;
                
                synchronized(lock) {
                    currentFactorial = fact;
                    ready = true;
                    lock.notify();
                    while(ready) {
                        try { lock.wait(); } 
                        catch (InterruptedException e) {}
                    }
                }
            }
        });

        Thread t2 = new Thread(() -> {
            double sum = 1.0; // Initial value
            for (int i = 0; i < 10; i++) {
                synchronized(lock) {
                    while(!ready) {
                        try { lock.wait(); } 
                        catch (InterruptedException e) {}
                    }
                    sum += 1.0 / currentFactorial;
                    System.out.println("Term " + (i+1) + ": 1/" + currentFactorial + " = " + 1.0 / currentFactorial);
                    ready = false;
                    lock.notify();
                }
            }
            System.out.println("Result: " + sum);
        });
        
        System.out.println("Term 0: 1 = 1.0");
        t1.start();
        t2.start();
    }
}
