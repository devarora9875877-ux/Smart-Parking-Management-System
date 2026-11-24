import java.util.ArrayList;

public class VehicleRegistry {

    private ArrayList<Vehicle> vehicles;
    private ArrayList<Customer> customers;

    public VehicleRegistry() {
        vehicles = new ArrayList<>();
        customers = new ArrayList<>();
    }

    public boolean registerVehicle(Vehicle vehicle) {
        for (Vehicle existingVehicle : vehicles) {
            if (existingVehicle.getRegistrationNumber()
                    .equalsIgnoreCase(vehicle.getRegistrationNumber())) {
                return false;
            }
        }

        vehicles.add(vehicle);
        return true;
    }

    public void registerCustomer(Customer customer) {
        customers.add(customer);
    }

    public Vehicle searchVehicle(String registrationNumber) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getRegistrationNumber()
                    .equalsIgnoreCase(registrationNumber)) {
                return vehicle;
            }
        }

        return null;
    }

    public boolean removeVehicle(String registrationNumber) {
        Vehicle vehicle = searchVehicle(registrationNumber);

        if (vehicle != null) {
            vehicles.remove(vehicle);
            return true;
        }

        return false;
    }

    public void displayAllVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles registered.");
            return;
        }

        for (Vehicle vehicle : vehicles) {
            vehicle.displayDetails();
            System.out.println("--------------------");
        }
    }

    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers registered.");
            return;
        }

        for (Customer customer : customers) {
            customer.displayCustomerDetails();
            System.out.println("--------------------");
        }
    }
}