public class Main {

    public static void main(String[] args) {

        VehicleRegistry registry = new VehicleRegistry();
        Admin admin = new Admin(1, "ParkingAdmin");

        Vehicle car = new Car("PB01AB1234", "Toyota", "Fortuner");
        Vehicle bike = new Bike("PB01XY5678", "Royal Enfield", "Classic 350");

        Customer customer1 = new Customer(101, "Dev", "9876543210", car);
        Customer customer2 = new Customer(102, "Rahul", "9876543211", bike);

        System.out.println("--- Registering Vehicles ---");
        admin.registerVehicle(registry, car);
        admin.registerVehicle(registry, bike);

        System.out.println("\n--- Registering Customers ---");
        admin.registerCustomer(registry, customer1);
        admin.registerCustomer(registry, customer2);

        System.out.println("\n--- All Vehicles ---");
        admin.displayVehicles(registry);

        System.out.println("\n--- All Customers ---");
        admin.displayCustomers(registry);

        System.out.println("\n--- Searching for a Vehicle ---");
        admin.searchVehicle(registry, "PB01AB1234");

        System.out.println("\n--- Testing Duplicate Registration ---");
        Vehicle duplicateCar = new Car("PB01AB1234", "Honda", "City");
        admin.registerVehicle(registry, duplicateCar);

        System.out.println("\n--- Removing a Vehicle ---");
        admin.removeVehicle(registry, "PB01XY5678");

        System.out.println("\n--- Vehicles After Removal ---");
        admin.displayVehicles(registry);
    }
}