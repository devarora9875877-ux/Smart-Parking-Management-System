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
        // BILLING, VALIDATION, PAYMENT AND HISTORY

        try {
            InputValidator validator = new InputValidator();
            ParkingHistory history = new ParkingHistory();

            String vehicleNumber = "PB01AB1234";
            String vehicleType = "Car";
            int hours = 3;

            // Input Validation
            if (!validator.isValidVehicleNumber(vehicleNumber)) {
                throw new ParkingException("Invalid vehicle number!");
            }

            if (!validator.isValidVehicleType(vehicleType)) {
                throw new ParkingException("Invalid vehicle type!");
            }

            if (!validator.isValidHours(hours)) {
                throw new ParkingException("Invalid parking hours!");
            }

            System.out.println("\n--- Billing ---");

            // Billing and Rate Calculation
            Billing bill = new Billing(vehicleNumber, hours);
            bill.generateBill(vehicleType);

            double amount = bill.getTotalAmount();

            // Payment
            System.out.println("\n--- Payment ---");
            Payment payment = new Payment(amount, "UPI");
            payment.makePayment();
            payment.displayPayment();

            // Parking History
            System.out.println("\n--- Parking History ---");
            history.addRecord(vehicleNumber, vehicleType, hours, amount);
            history.displayHistory();

        } catch (ParkingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
