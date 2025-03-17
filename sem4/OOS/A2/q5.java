class Main {
    static Object lock=new Object();
    static int c=1;
    static int sumEven=0,sumOdd=0;
    
    boolean readyEven =false,readyOdd=false;
    
    static class OddThread extends Thread
    {
        public void run()
        {
            for(int i=1;i<=99;i+=2)
            {
                sumOdd+=i;
                
            }
        }
    }
    
    static class EvenThread extends Thread
    {
        public void run()
        {
            for(int i=2;i<=100;i+=2)
            {
                sumEven+=i;
                
            }
        }
    }
    
    public static void main(String args[])
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
        
        System.out.println(sumEven+sumOdd);
    }
}
