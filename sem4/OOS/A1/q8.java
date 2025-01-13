class Account
{
    int acno;
    String name;
    float bal;
    Account(int a,String n,float b)
    {
        acno=a;
        name=n;
        bal=b;
    }
    int get_acno()
    {
        return acno;
    }
    float get_bal()
    {
        return bal;
    }
    String get_name()
    {
        return name;
    }
    void set_acno(int x)
    {
        acno=x;
    }
    void set_bal(float x)
    {
        bal=x;
    }
    void set_name(String x)
    {
        name=x;
    }
    
}

class SavingsAccount extends Account
{
    float rate;
    SavingsAccount(int a,String n,float b,float r)
    {
        super(a,n,b);
        rate=r;
    }
    void calc()
    {
        System.out.println("Yearly interest is : "+ ((bal*rate*12)/100.0));
    }
    
}
class Manager
{
    public static void main(String args[])
    {
        Account acc[]=new Account[5];
        Manager man=new Manager();
        SavingsAccount s1=new SavingsAccount(101,"A",5000f,5.1f);
        acc[0]=s1;
        man.print(acc[0]);
        s1.calc();
        SavingsAccount s2=new SavingsAccount(102,"B",6000f,5.2f);
        acc[1]=s2;
        man.print(acc[1]);
        s2.calc();
        acc[2]=new CurrentAccount(103,"C",7000f);
        man.print(acc[2]);
        acc[3]=new CurrentAccount(104,"D",8000f);
        man.print(acc[3]);
        acc[4]=new CurrentAccount(105,"E",9000f);
        man.print(acc[4]);
    }
    void print(Account ob)
    {
        System.out.println("Name: "+ob.get_name()+"\nAccount no.: "+ob.get_acno()+"\nBalance: "+ob.get_bal());
    }
}
class CurrentAccount extends Account
{
    CurrentAccount(int a,String n,float b)
    {
        super(a,n,b);
    }
}
