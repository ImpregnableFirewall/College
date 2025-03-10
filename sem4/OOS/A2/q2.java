class Main {
    
    static Object lock = new Object();

    
    static int counter = 1;

    
    static class EvenThread extends Thread
    {
        public void run() 
        {
            for (int i = 2; i <= 8; i += 2)
            {
                synchronized (lock) 
                {
                    while (counter % 2 != 0) {
                        try 
                        {
                            lock.wait();
                        } 
                        catch (InterruptedException e) 
                        {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.print(i + " ");
                    counter++;
                    lock.notify();
                }
            }
        }
    }

    
    static class OddThread extends Thread 
    {
        public void run() 
        {
            for (int i = 1; i <= 7; i += 2) 
            {
                synchronized (lock) 
                {
                    while (counter % 2 == 0) 
                    {
                        try 
                        {
                            lock.wait();
                        } 
                        catch (InterruptedException e) 
                        {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.print(i + " ");
                    counter++; 
                    lock.notify();
                }
            }
        }
    }

    public static void main(String[] args) 
    {
        
        Thread evenThread = new EvenThread();
        Thread oddThread = new OddThread();
       
        evenThread.start();
        oddThread.start();
       
        try 
        {
            evenThread.join();
            oddThread.join();
        } 
        catch (InterruptedException e) 
        {
            Thread.currentThread().interrupt();
        }
    }
}
