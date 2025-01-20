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
        System.out.println("Age: "+get_age()+"\nWeight: "+get_weight()+"\nHeight: "+get_height()+" cm\nDOB: "+get_dob()+"\nAddress: "+get_address());
    }
}

class Employee extends Person
{
    float sal;
    String doj;
    int exp;
    Employee(int a, int h, float w, String d, String ad,float s, String dj,int e)
    {
        super(a,h,w,d,ad);
        sal=s;
        doj=dj;
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
    Technician(int a, int h, float w, String d, String ad,float s, String dj,int e)
    {
        super(a,h,w,d,ad,s,dj,e);
    }
    void read()
    {
        super.read();
        System.out.println("Technician ready");
    }
}
class Professor extends Employee
{
    String courses[]=new String[2];
    String advisee[]=new String[3];
    Professor(int a, int h, float w, String d, String ad,String c[],String adv[],float s, String dj,int e)
    {
        super(a,h,w,d,ad,s,dj,e);
        courses=c;
        advisee=adv;
    }
    void read()
    {
        super.read();
        int i=0;
        System.out.println("Courses teaching: ");
        for(;i<=1;i++)
        {
            System.out.println(courses[i]);
        }
        System.out.println("Advisee list: ");
        for(;i<=2;i++)
        {
            System.out.println(advisee[i]);
        }
    }
}
class Student extends Person
{
    int roll;
    float subs[]=new float[5];
    Student(int a, int h, float w, String d, String ad,float s[],int r)
    {
        super(a,h,w,d,ad);
        subs=s;
        roll=r;
    }
    void calc()
    {
        float s=0;
        int i=0;
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
        System.out.println("Marks: ");
        for(;i<=4;i++)
        {
            System.out.println("Sub"+(i+1)+": "+subs[i]);
        }
        calc();
    }
}

class Main
{
    public static void main(String args[])
    {
        float mar[]={100f,98f,99f,95f,70f};
        Person s=new Student(21,180,71,"12/05/2004","Kol-93",mar,13);
        System.out.println("\nStudent: ");
        print(s);
        String c[]={"Engg.","Science"};
        String a[]={"Soham","Anushka","Ritam"};
        Person p=new Professor(30,170,65,"12/05/1995","Barasat",c,a,10000f,"12/05/2018",8);
        System.out.println("\nProfessor: ");
        print(p);
        Person t=new Technician(25,190,60,"12/05/2000","Durgapur",5000f,"12/05/2019",7);
        System.out.println("\nTechnician: ");
        print(t);
    }
    static void print(Person p)
    {
        p.read();
    }
}
