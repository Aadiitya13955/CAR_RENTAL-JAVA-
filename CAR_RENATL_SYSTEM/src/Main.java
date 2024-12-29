import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String CID;
    private String Brand;
    private String Model;
    private double price;
    private boolean isAvailable;

    public Car(String CID, String Brand, String Model, double price) {
        this.CID = CID;
        this.Brand = Brand;
        this.Model = Model;
        this.price = price;
        this.isAvailable = true; // Changed 'True' to 'true'
    }

    public String getCID() {
        return CID;
    }

    public String getBrand() {
        return Brand;
    }

    public String getModel() {
        return Model;
    }

    public double calculatePrice(int rentDay) {
        return price * rentDay;
    }

    public boolean isAvailable() { // Corrected method name
        return isAvailable;
    }

    public void rent() {
        isAvailable = false; // Corrected spelling
    }

    public void returnCar() { // Corrected method name
        isAvailable = true; // Corrected spelling
    }
}

class Customer {
    private String CustomerID;
    private String NAME;

    public Customer(String CustomerID, String NAME) {
        this.CustomerID = CustomerID;
        this.NAME = NAME;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public String getNAME() {
        return NAME;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }
}

class CarRentalSystem { // Removed parentheses
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) { // Corrected method call
        customers.add(customer); // Changed 'Customer.add' to 'customers.add'
    }

    public void rentCar(Car car, Customer customer, int days) { // Corrected method name
        if (car.isAvailable()) { // Corrected method name
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent");
        }
    }

    public void returnCar(Car car) { // Corrected method name
        car.returnCar(); // Corrected method name
        Rental rentalToRemove = null; // Changed variable name for clarity
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }

        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("Car returned");
        } else {
            System.out.println("Car was not rented");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(" ** CAR RENTAL SYSTEM **");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("\n RENT A CAR \n");
                System.out.print("Enter your name: ");
                String NAME = scanner.nextLine(); // Added to capture customer name

                System.out.println("Cars Available: ");
                for (Car car : cars) {
                    if (car.isAvailable()) { // Corrected method name
                        System.out.println(car.getCID() + " " + car.getBrand() + " " + car.getModel());
                    }
                }
                System.out.print("\nEnter the Car ID: ");
                String carID = scanner.nextLine();

                System.out.print("\nEnter the number of days for rental: ");
                int rentalDays = scanner.nextInt(); // Changed variable name to follow naming conventions
                scanner.nextLine();

                Customer customerss = new Customer("CD" + (customers.size() + 1), NAME);
                addCustomer(customerss);

                Car selectedCar = null; // Changed variable name for clarity
                for (Car car : cars) {
                    if (car.getCID().equals(carID) && car.isAvailable()) { // Corrected method name
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + customerss.getCustomerID());
                    System.out.println("Customer NAME: " + customerss.getNAME() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm Rental (Y/N): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, customerss, rentalDays); // Corrected method call
                        System.out.println("\nCar rented successfully");
                    } else {
                        System.out.println("\nRental canceled");
                    }
                } else {
                    System.out.println("\nCar selection is invalid or car not available");
                }
            } else if (choice == 2) {
                System.out.println("\nReturn a car");
                System.out.print("Enter the car ID you want to return: ");
                String CID = scanner.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCID().equals(CID) && !car.isAvailable()) { // Changed 'carID' to 'CID'
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getNAME());
                    } else {
                        System.out.println("Car was not rented or the rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented");
                }

            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option");
            }
        }

        System.out.println("\nThank you for using the car rental system");
    }

}

public class Main {
    public static void main(String[] args){
        CarRentalSystem rentalSystem = new CarRentalSystem();
        Car car1 = new Car("007", "Toyota", "Hilux", 80);
        Car car2 = new Car("369", "Lexus", "GX 500", 230);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.menu();
    }

}
