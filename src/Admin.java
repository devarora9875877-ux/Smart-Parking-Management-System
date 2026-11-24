public class Admin {

    private int adminId;
    private String username;

    public Admin(int adminId, String username) {
        this.adminId = adminId;
        this.username = username;
    }
    public void displayAdminDetails() {
        System.out.println("Admin ID: " + adminId);
        System.out.println("Username: " + username);
    }
    public void registerVehicle(VehicleRegistry registry, Vehicle vehicle) {
        boolean registered = registry.registerVehicle(vehicle);

        if (registered) {
            System.out.println("Vehicle registered successfully.");
        } else {
            System.out.println("Vehicle registration number already exists.");
        }
    }
    public void registerCustomer(VehicleRegistry registry, Customer customer) {
        registry.registerCustomer(customer);
        System.out.println("Customer registered successfully.");
    }
    public void displayVehicles(VehicleRegistry registry) {
        registry.displayAllVehicles();
    }

    public void displayCustomers(VehicleRegistry registry) {
        registry.displayAllCustomers();
    }
    public void searchVehicle(VehicleRegistry registry, String registrationNumber) {
        Vehicle vehicle = registry.searchVehicle(registrationNumber);

        if (vehicle != null) {
            vehicle.displayDetails();
        } else {
            System.out.println("Vehicle not found.");
        }
    }
    public void removeVehicle(VehicleRegistry registry, String registrationNumber) {
        boolean removed = registry.removeVehicle(registrationNumber);

        if (removed) {
            System.out.println("Vehicle removed successfully.");
        } else {
            System.out.println("Vehicle not found.");
        }
    }
}