class stack
{
    int size;
    int arr[];
    int top;
    stack(int s)
    {
        size=s;
        arr=new int[size];
        top=-1;
    }

    void push(int x)
    {
        if(top!=size-1)
        {
            arr[++top]=x;
        }
        else
        {
            System.out.println("Stack full!");
        }
    }
    void pop()
    {
        if(top!=-1)
        System.out.println("Popped: "+arr[top--]);
        else
        {
            System.out.println("Stack empty!");
        }
    }
    void print()
      {
        int i=top;
        System.out.println("Elements: ");
        for(;i>=0;i--)
        {
            System.out.println(arr[i]);
        }
    }

    public static void main(String args[])
    {
        stack s1=new stack(30);
        s1.push(10);
        s1.push(20);
        s1.push(30);
        s1.push(15);
        s1.push(9);
        s1.print();
        s1.pop();
        s1.pop();
        s1.pop();
        s1.print();

    }
}

