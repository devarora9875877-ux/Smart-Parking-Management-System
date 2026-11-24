public class Car extends Vehicle {

    public Car(String registrationNumber, String brand, String vehicleName) {
        super(registrationNumber, brand, vehicleName);
    }

    @Override
    public String getVehicleType() {
        return "Car";
    }
}