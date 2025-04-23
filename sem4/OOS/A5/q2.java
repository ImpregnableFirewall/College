interface Shape
{
    void draw();
}
class Circle implements Shape
{
    public void draw()
    {
        System.out.println("Drawing Circle");
    }
}

class Rectangle implements Shape
{
    public void draw()
    {
        System.out.println("Drawing Rectangle");
    }
}
class ShapeDecorator implements Shape
{
    public Shape shape;
    public ShapeDecorator(Shape shape)
    {
        this.shape = shape;
    }
    public void draw()
    {
        this.shape.draw();
    }
}
class RedShapeDecorator extends ShapeDecorator
{
    RedShapeDecorator(Shape shape)
    {
        super(shape);
    }
    private void setRedBorder()
    {
        System.out.println("Adding RedBorder");
    }
    public void draw()
    {
        super.draw();
        this.setRedBorder();
    }
}

class DecoratorPatternDemo
{
    public static void main(String[] args)
    {
        Circle circle = new Circle();
        Rectangle rectangle = new Rectangle();
        circle.draw();
        rectangle.draw();
        RedShapeDecorator circleDecorator = new RedShapeDecorator((Shape) circle);
        RedShapeDecorator rectangleDecorator = new RedShapeDecorator((Shape)
        rectangle);
        circleDecorator.draw();
        rectangleDecorator.draw();
    }
}
