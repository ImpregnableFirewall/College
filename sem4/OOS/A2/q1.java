public class Main
{
    public static void main(String args[])
    {
        ChildThread ob=new ChildThread();
        ob.start();
        System.out.println("In main thread");
    }
}
class ChildThread extends Thread
{
    public void run()
    {
        System.out.println("In child thread");
    }
}
