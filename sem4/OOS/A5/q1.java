class MediatorPatternDemo
{
    public static void main(String[] args)
    {
        User user1 = new User("Nolan");
        User user2 = new User("Mark");
        User user3 = new User("Conquest");
        Mediator chatRoom = new ChatRoom();
        chatRoom.showMessage(user1,"Think ,Mark ,think");
        chatRoom.showMessage(user2,"Seasalt , HELP ME");
        chatRoom.showMessage(user3,"Prepare for my entrance, worm");
    }
}
class User
{
    private String name;
    User(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return this.name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void sendMessage(String message)
    {
        System.out.println(name+" : "+message);
    }
}
interface Mediator
{
    void showMessage(User user,String msg);
}
class ChatRoom implements Mediator
{
    public void showMessage(User user,String msg)
    {
        user.sendMessage(msg);
    }
}
