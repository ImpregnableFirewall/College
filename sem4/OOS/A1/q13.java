class Automobile {
    String make;
    String type;
    int maxSpeed;
    double price;
    double mileage;
    String registrationNumber;

    public Automobile(String make, String type, int maxSpeed, double price, double mileage, String registrationNumber) {
        this.make = make;
        this.type = type;
        this.maxSpeed = maxSpeed;
        this.price = price;
        this.mileage = mileage;
        this.registrationNumber = registrationNumber;
    }

    public void displayInfo() {
        System.out.println("Make: " + make);
        System.out.println("Type: " + type);
        System.out.println("Max Speed: " + maxSpeed + " km/h");
        System.out.println("Price: Rs." + price);
        System.out.println("Mileage: " + mileage + " km/l");
        System.out.println("Registration Number: " + registrationNumber);
    }
}

class Truck extends Automobile {
    int capacity;
    String hoodType;
    int noOfWheels;

    public Truck(String make, String type, int maxSpeed, double price, double mileage, String registrationNumber, int capacity, String hoodType, int noOfWheels) {
        super(make, type, maxSpeed, price, mileage, registrationNumber);
        this.capacity = capacity;
        this.hoodType = hoodType;
        this.noOfWheels = noOfWheels;
    }

    public void displayInfo() {
        super.displayInfo();
        System.out.println("Capacity: " + capacity + " tons");
        System.out.println("Hood Type: " + hoodType);
        System.out.println("Number of Wheels: " + noOfWheels);
    }
}

class Car extends Automobile {
    int noOfDoors;
    int seatingCapacity;

    public Car(String make, String type, int maxSpeed, double price, double mileage, String registrationNumber, int noOfDoors, int seatingCapacity) {
        super(make, type, maxSpeed, price, mileage, registrationNumber);
        this.noOfDoors = noOfDoors;
        this.seatingCapacity = seatingCapacity;
    }

    public void displayInfo() {
        super.displayInfo();
        System.out.println("Number of Doors: " + noOfDoors);
        System.out.println("Seating Capacity: " + seatingCapacity);
    }
}

public class Main {
    static void dispInfo(Automobile a)
    {
        a.displayInfo();
    }
    public static void main(String[] args) {
        Automobile truck = new Truck("Volvo", "Truck", 120, 5000000, 5, "TRK123", 10, "Covered", 6);
        Automobile car = new Car("Toyota", "Sedan", 180, 3000000, 15, "CAR456", 4, 5);

        System.out.println("Truck Details:");
        dispInfo(truck);
        System.out.println();
        
        System.out.println("Car Details:");
        dispInfo(car);
    }
}
