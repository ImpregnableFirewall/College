import java.util.Scanner;

class Student {
    String name;
    int roll;
    String[] sub;

    Student(String n, int r, String[] s) {
        name = n;
        roll = r;
        sub = s;
    }

    String getName() {
        return name;
    }

    int getRollNo() {
        return roll;
    }

    String[] getsub() {
        return sub;
    }
}

class TabulationSheet {
    String sub;
    int[] roll;
    int[] marks;

    TabulationSheet(String s, int m[],int r[]) {
        sub = s;
        roll = r;
        marks = m;
    }

    int getMark(int rollNo) {
        int i=0;
        for (; i < roll.length; i++) {
            if (roll[i] == rollNo) {
                return marks[i];
            }
        }
        return -1; 
    }

    String getSubject() {
        return sub;
    }
}

class MarkSheet {
    String name;
    String[] sub;
    int[] marks;

    public MarkSheet(String n, String s[],int m[]) {
        name = n;
        sub = s;
        marks = m;
    }

}

class Main {
    
    static void printMarkSheet(Student s, MarkSheet m) {
        System.out.println("Name: " + s.getName());
        System.out.println("Roll No. : " + s.getRollNo());
        System.out.println("Marks : " );
        int i=0;
        for (i = 0; i < 5; i++) {
            System.out.println(m.sub[i] + ": " + m.marks[i]);
        }
        System.out.println();
    }
    public static void main(String[] args) {
        String[] sub = {"Math", "Physics", "Chemistry", "Computer", "English"};

        Student[] students = {
            new Student("Alice", 101, sub),
            new Student("Bob", 102, sub),
            new Student("Charlie", 103, sub)
        };
        
        int[][] marksData = {
            {85, 90, 75},
            {80, 88, 70},
            {78, 84, 65},
            {92, 85, 80},
            {88, 79, 82}
        };

        TabulationSheet[] tabSheets = new TabulationSheet[5];
        int i;
        for (i = 0; i <5; i++) {
            tabSheets[i] = new TabulationSheet(sub[i],marksData[i],new int[]{101,102,103});
        }

        MarkSheet[] markSheets = new MarkSheet[3];
        for (i = 0; i < 3; i++) {
            int j=0;
            int markCol[]=new int[5];
            for(;j<5;j++)
            markCol[j]=marksData[j][i];
            markSheets[i] = new MarkSheet(students[i].getName(), sub,markCol);

        }

        for(i=0;i<3;i++)
        {
            printMarkSheet(students[i], markSheets[i]);
        }
    }
}
