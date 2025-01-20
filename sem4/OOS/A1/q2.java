class student
{
    String name;
    double s1,s2,s3;
    student(double s1,double s2,double s3,String name)
    {
        this.name=name;
        this.s1=s1;
        this.s2=s2;
        this.s3=s3;
    }
    void avg()
    {
        System.out.println("Avg: "+(s1+s2+s3)/3.0);
    }
    void disp()
    {
        System.out.println("Name: "+name+" total: "+(s1+s2+s3));
    }
    public static void main(String args[])
    {
        student st1=new student(10,20,30,"A");
        st1.disp();
        st1.avg();
    }
}
~
