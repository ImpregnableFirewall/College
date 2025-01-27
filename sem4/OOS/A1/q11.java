class Date {
    int day, month, year;
    int[] nonLeapDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int[] leapDays = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    Date() {
        day = 1;
        month = 1;
        year = 1970;
    }

    Date(int day) {
        this.day = day;
        this.month = 1;
        this.year = 1970;
    }

    Date(int day, int month) {
        this.day = day;
        this.month = month;
        this.year = 1970;
    }

    Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    boolean isLeapYear(int year) {
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    }

    int daysInMonth(int month, int year) {
        if (isLeapYear(year)) {
            return leapDays[month - 1];
        } else {
            return nonLeapDays[month - 1];
        }
    }

    void getPreviousDay() {
        if (day > 1) {
            day--;
        } else {
            if (month == 1) {
                month = 12;
                year--;
            } else {
                month--;
            }
            day = daysInMonth(month, year);
        }
    }

    void getNextDay() {
        if (day < daysInMonth(month, year)) {
            day++;
        } else {
            day = 1;
            if (month == 12) {
                month = 1;
                year++;
            } else {
                month++;
            }
        }
    }

    void printDate() {
        System.out.printf("%02d/%02d/%d\n", day, month, year);
    }
}

class Main {
    public static void main(String[] args) {
        Date d1 = new Date();
        d1.printDate();
        d1.getPreviousDay();
        System.out.print("Previous day: ");
        d1.printDate();
        d1.getNextDay();
        d1.getNextDay();
        System.out.print("Next day: ");
        d1.printDate();
        
        Date d2 = new Date(29);
        d2.printDate();
        d2.getPreviousDay();
        System.out.print("Previous day: ");
        d2.printDate();
        d2.getNextDay();
        d2.getNextDay();
        System.out.print("Next day: ");
        d2.printDate();
        
        Date d3 = new Date(12,5);
        d3.printDate();
        d3.getPreviousDay();
        System.out.print("Previous day: ");
        d3.printDate();
        d3.getNextDay();
        d3.getNextDay();
        System.out.print("Next day: ");
        d3.printDate();
        
        Date d4 = new Date(12,5,2004);
        d4.printDate();
        d4.getPreviousDay();
        System.out.print("Previous day: ");
        d4.printDate();
        d4.getNextDay();
        d4.getNextDay();
        System.out.print("Next day: ");
        d4.printDate();
    }
}
