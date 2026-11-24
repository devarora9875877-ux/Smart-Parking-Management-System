public class Bike extends Vehicle {
    public Bike(String registrationNumber, String brand, String vehicleName) {
        super(registrationNumber, brand, vehicleName);
    }
    @Override
    public String getVehicleType() {
        return "Bike";
    }
}