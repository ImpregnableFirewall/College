class Room
{
    float h,w,b;
    Room(float h, float w,float b)
    {
        this.w=w;;
        this.b=b;
        this.h=h;
    }
    void vol()
    {
        System.out.println("Volume is: "+ (h*w*b));
    }
}
class RoomDemo
{
    public static void main(String args[])
    {
        Room r1=new Room(1,2,3);
        Room r2=new Room(1.1f,2.2f,3.3f);
        r1.vol();
        r2.vol();
    }
}
