class Person
{
    int age,height;
    float weight;
    String dob,address;
    Person(int a, int h, float w, String d, String ad)
    {
        age=a;
        height=h;
        weight=w;
        dob=d;
        address=ad;
    }
    int get_age()
    {
        return age;
    }
    int get_height()
    {
        return height;
    }
    float get_weight()
    {
        return weight;
    }
    String get_dob()
    {
        return dob;
    }
    String get_address()
    {
        return address;
    }
    void read()
    {
        System.out.println("Name: "+get_name()+"\nAge: "+get_age()+"\nWeight: "+get_weight()+"\nHeight: "+get_height()+" cm\nDOB: "+get_dob()+"\nAddress: "+get_address());
    }
}

class Employee extends Person
{
    float sal;
    String doj;
    int exp;
    class Employee(int a, int h, float w, String d, String ad,float s, String d,int e)
    {
        super(a,h,w,d,ad);
        sal=s;
        doj=d;
        exp=e;
    }
    void read()
    {
        super.read();
        System.out.println("Salary: "+sal+"\nJoining: "+doj+"\nExperience: "+exp);
    }
}
class Technician extends Employee
{
    
}
class Professsor extends Employee
{
    
}
class Student extends Person
{
    int roll;
    float subs[]=new float[5];
    class Employee(int a, int h, float w, String d, String ad,float s[],int r)
    {
        super(a,h,w,d,ad);
        subs=s;
        roll=r;
    }
    void calc()
    {
        float s=0;
        for(;i<=4;i++)
        {
            s+=subs[i];
        }
        System.out.println("Total: "+s);
        if(s>400)
        System.out.println("Grade A");
        else if(s>300)
        System.out.println("Grade B");
        else if(s>200)
        System.out.println("Grade C");
        else if(s>100)
        System.out.println("Grade D");
        else
        System.out.println("Grade E");
        
    }
    void read()
    {
        super.read();
        System.out.println("Roll no.: "+roll);
        int i=0;
        System.out.println("Marks: ")
        for(;i<=4;i++)
        {
            System.out.println("Sub"+(i+1)+": "+subs[i]);
        }
        calc();
    }
}
