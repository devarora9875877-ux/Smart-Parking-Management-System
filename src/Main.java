public class Main {

    public static void main(String[] args) {

        Vehicle car = new Car("PB01AB1234", "Toyota", "Fortuner");
        Vehicle bike = new Bike("PB01XY5678", "Royal Enfield", "Classic 350");

        Customer customer1 = new Customer(101, "Dev", "9876543210", car);
        Customer customer2 = new Customer(102, "Rahul", "9876543211", bike);

        System.out.println("--- Customer 1 ---");
        customer1.displayCustomerDetails();

        System.out.println("\n--- Customer 2 ---");
        customer2.displayCustomerDetails();
    }
}