
class Emp
{
    String name,address;
    int id;
    float sal;
    Emp(String n,String a,int i,float s)
    {
        name=n;
        address=a;
        id=i;
        sal=s;
    }
    void read()
    {
        System.out.println("Name: "+name+"\nAddress: "+address+"\nEmployee Id: "+id+"\nMonthly Salary: "+sal);
    }
}
class Dept
{
    String name,loc;
    Emp employee[]=new Emp[5];
    Dept(String n, String l)
    {
        name=n;
        loc=l;
    }
    void add(int j,String n,String a,int i,float s)
    {
        employee[j]=new Emp(n,a,i,s);
    }
    void remove(int i)
    {
        System.out.println("employee terminated");
        employee[i]=null;
    }
    public static void main(String args[])
    {
        float s=0;
        Dept d=new Dept("IT","SL");
        d.add(0,"A","1001a",101,5000f);
        d.add(1,"B","1002b",102,6000f);
        d.add(2,"C","1003c",103,7000f);
        d.add(3,"D","1004d",104,8000f);
        d.add(4,"E","1005e",105,9000f);
        int i=0;
        for(;i<=4;i++)
        {
            d.employee[i].read();
            s+=d.employee[i].sal*12;
        }
        System.out.println("Yearly expenditure: "+ s);
        
    }
}
