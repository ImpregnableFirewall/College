interface Fruit
{
	void hasAPeel();
}

interface Vegetable
{
	void hasARoot();
}

class Tomato implements Fruit,Vegetable
{
	public void hasAPeel()
	{
		System.out.println("Tomato has a peel");
	}
	
	public void hasARoot()
	{
		System.out.println("Tomato has a root!");
	}
}

class Main
{
	public static void main(String args[])
	{
		Tomato obj = new Tomato();
		obj.hasAPeel();
		obj.hasARoot();
	}
}
