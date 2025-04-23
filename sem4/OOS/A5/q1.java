class FactoryPatternDemo
{
    public static void main(String[] args)
    {
        ShapeFactory shapeFactory = new GetCircle();
        Shape shape = shapeFactory.getShape();
        shape.draw();
        shapeFactory = new GetSquare();
        shape = shapeFactory.getShape();
        shape.draw();
        shapeFactory = new GetRectangle();
        shape = shapeFactory.getShape();
        
        shape.draw();
    }
}
interface Shape
{
    void draw();
}
class Circle implements Shape
{
    public void draw()
    {
        System.out.println("Circle drawn");
    }
}
class Square implements Shape
{
    public void draw()
    {
        System.out.println("Square drawn");
    }
}
class Rectangle implements Shape
{
    public void draw()
    {
        System.out.println("Rectangle drawn");
    }
}

abstract class ShapeFactory
{
    abstract Shape getShape();
}
class GetCircle extends ShapeFactory
{
    public Shape getShape()
    {
        return new Circle();
    }
}
class GetSquare extends ShapeFactory
{
    public Shape getShape()
    {
        return new Square();
    }
}
class GetRectangle extends ShapeFactory
{
    public Shape getShape()	
    {
        return new Rectangle();
    }
}
