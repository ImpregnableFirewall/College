import java.io.*;
import java.util.*;

class help{
    public static void helplist(){
        System.out.println(" > Commands are : ------------------------------------------- < ");
        System.out.println(" > add <task>       ----- adds a task to the list             < ");
        System.out.println(" > edt <task>       ----- edits specified task in the list    < ");
        System.out.println(" > cpt <task>       ----- completes the specified task        < ");
        System.out.println(" > rem <task>       ----- removes the specified task          < ");
        System.out.println(" > dis incmp        ----- displays only the incomplete tasks  < ");
        System.out.println(" > dis cmp          ----- displays only the complete tasks    < ");
        System.out.println(" > dis              ----- displays the task List full         < ");
        System.out.println(" > q                ----- quit                                < ");
    }
}

class task{
    String name;
    String desc;
    boolean done=false;

    task(String s){
        name=s;
    }

    void complete(){
        done=true;
    }
    void setCompletion(boolean b){
        done=b; 
    }
    void setName(String s){
        name=s;
    }

    void setDesc(String s){
        desc=s;
    }

    String getName(){
        return name;
    }

    boolean getCompletion(){
        return done;
    }
}

class TaskList{
    ArrayList<task> tl= new ArrayList<task>();

    void addTask(String name, String description){
        task temp=new task(name);
        temp.desc= description;
        temp.done=false;
        tl.add(temp);
        System.out.println(" >>> Task added successfully\n");
    }

    void completeTask(String tname){
        for(int i=0;i<tl.size();i++){
            if(tname.equalsIgnoreCase(tl.get(i).getName())){
                tl.get(i).complete();
                System.out.println(" >>> Task marked as complete\n");
                return;
            }
        }
        System.out.println(" >> Invalid Task Name\n");
    }

    void removeTask(String tname){
        for(int i=0;i<tl.size();i++){
            if(tname.equalsIgnoreCase(tl.get(i).getName())){
                tl.remove(i);
                System.out.println(" >>> Task successfully removed \n");
                return;
            }
        }
        System.out.println(" >> Invalid Task Name \n");
    }

    void editTask(String tname){
        task temp= new task("");
        boolean got=false;
        for(int i=0;i<tl.size();i++){
            if(tname.equalsIgnoreCase(tl.get(i).getName())){
                temp=tl.get(i);
                tl.remove(i);
                got=true;
            }
        }
        if(!got){
            System.out.println(" >> Invalid Task Name");
            return;
        }
        


        System.out.println(" >> Enter 1 to edit name");
        System.out.println(" >> Enter 2 to edit Description");
        System.out.println(" >> Enter 3 to edit status");

        Scanner in=new Scanner(System.in);
        String chh= in.nextLine();
        int ch =Integer.valueOf(chh);
        switch(ch){
            case 1:
            System.out.println(" >> Enter new Name ");
            String s= in.nextLine();
            temp.setName(s);
            break;

            case 2:
            System.out.println(" >>  Enter new Description");
            String ss =in.nextLine();
            temp.setDesc(ss);
            break;

            case 3:
            System.out.println(" >>  Enter new Status (true for done, false for not done) ");
            boolean st=in.nextBoolean();
            temp.setCompletion(st);
            break;

            default:
            System.out.println(" >> Invalid choice");
        }
        tl.add(temp);
        System.out.println();
    }

    void display(){
        for(int i=0;i<tl.size();i++){
            System.out.println(" > Task "+i+" : ");
            System.out.println("   Name = "+tl.get(i).getName());
            System.out.println("   Description = "+tl.get(i).desc);
            String k=(tl.get(i).getCompletion())?"Done":"Not Done";
            System.out.println("   Status = "+k);
            System.out.println();
        }
        System.out.println();
    }

    void disp(String param){
        if(param.equalsIgnoreCase("cmp")){
            
            for(int i=0;i<tl.size();i++){
                if(tl.get(i).getCompletion()==true){
                    System.out.println(" > Task "+i+" : ");
                    System.out.println("   Name = "+tl.get(i).getName());
                    System.out.println("   Description = "+tl.get(i).desc);
                    System.out.println("   Status = "+((tl.get(i).getCompletion())?"Done":"Not Done"));
                    System.out.println();
                }
            }
        }

        else if(param.equalsIgnoreCase("incmp")){
            for(int i=0;i<tl.size();i++){
                if(tl.get(i).getCompletion()==false){
                    System.out.println(" > Task "+i+" : ");
                    System.out.println("   Name = "+tl.get(i).getName());
                    System.out.println("   Description = "+tl.get(i).desc);
                    System.out.println("   Status = "+((tl.get(i).getCompletion())?"Done":"Not Done"));
                    System.out.println();
                }
            }
        }

        else{
            System.out.println(" >> Invalid choice ");
        }
        System.out.println();
    }
}

public class taskmanager {
    
    void reader(String command,TaskList tList){
        int wordCnt=0;
        int spcCnt=0;
        Scanner in= new Scanner(System.in);
        if(command.length()<3){
            System.out.println(" >> Invalid Input");
            return;
        }
        for(int i=0;i<command.length();i++){
            if(command.charAt(i)==' ')
            {
                spcCnt++;
            }
        }
        wordCnt=spcCnt+1;
        String name="";
        String instr=command.substring(0,3);
        switch(instr){
            case "dis": 
                if(wordCnt<=1){
                   tList.display();
                }
                else if(wordCnt==2){
                    String p= command.substring(4);
                    tList.disp(p);
                }
                break;

            case "add":  
                name=command.substring(4);
                System.out.println(" >>> Enter Description ");
                String desc= in.nextLine();
                tList.addTask(name, desc);
                break;

            case "edt":
                name=command.substring(4);
                tList.editTask(name);
                break;
            case "rem":
                name =command.substring(4);
                tList.removeTask(name);
                break;
            case "cpt":
                name=command.substring(4);
                tList.completeTask(name);
                break;
            default:
                System.out.println(" >> Invalid input\n");
        }
        
    }

    public static void main(String[] args) throws IOException{
        taskmanager tsm= new taskmanager();
        TaskList tl= new TaskList();
        InputStreamReader is= new InputStreamReader(System.in);
        BufferedReader br= new BufferedReader(is);
        System.out.println(" // Type help to see command list");
        while(true){
            
            String s= br.readLine();
            if(s.isEmpty() || s==null)continue;
            if(s.charAt(0)=='q'){
                break;
            }
            else if(s.equalsIgnoreCase("help")){
                help.helplist();
            }
            else{
                tsm.reader(s, tl);
            }
        }
        br.close();
    }
}
