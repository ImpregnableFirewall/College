interface Shape
{
    void area();
    void draw();
    void rotate();
    void move();
}

class Circle implements Shape
{
    float radius;
    int n;
    Circle(float r,int n)
	{
        radius=r;
        this.n=n;
	}

	public void area()
    {
        System.out.println("Area of circle "+n+": "+ (3.14*radius*radius));
    }    

    public void draw()
    {
        System.out.println("Drawing a circle");

    }

    public void rotate()
    {
        System.out.println("Rotating the circle");
    }

    public void move()
    {
        System.out.println("Moving the circle");
    }

	void getDetails()
	{
		area();
		draw();
		rotate();
		move();
	}
}

class Rectangle implements Shape
{       
    float len,bre;
    int n;
	Rectangle(float l, float b,int n)
	{
		len=l;
        bre=b;
        this.n=n;
	}

    public void area()
    {
        System.out.println("Area of rectangle "+n+" : "+ (len*bre));
    }

    public void draw()
    {
        System.out.println("Drawing a rectangle");
    }

    public void rotate()
    {
        System.out.println("Rotating the rectangle");
    }

    public void move()
    {
        System.out.println("Moving the rectangle");
    }

	void getDetails()
	{
		area();
		draw();
		rotate();
		move();
	}
}

class Main
{
	public static void main(String args[])
	{
		Circle c1 = new Circle(5f,1);
		c1.getDetails();

		Circle c2 = new Circle(7.6f,2);
		c2.getDetails();

		Rectangle r1 = new Rectangle(3f,4f,1);
		r1.getDetails();

		Rectangle r2 = new Rectangle(2.3f,3.1f,2);
		r2.getDetails();

		Rectangle r3 = new Rectangle(1f,2f,3);
		r3.getDetails();
	}
}
