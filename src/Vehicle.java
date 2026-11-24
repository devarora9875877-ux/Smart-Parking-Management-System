public abstract class Vehicle {

    private String registrationNumber;
    private String brand;
    private String vehicleName;

    public Vehicle(String registrationNumber, String brand, String vehicleName) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.vehicleName = vehicleName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public abstract String getVehicleType();

    public void displayDetails() {
        System.out.println("Registration Number: " + registrationNumber);
        System.out.println("Brand: " + brand);
        System.out.println("Vehicle Name: " + vehicleName);
        System.out.println("Vehicle Type: " + getVehicleType());
    }
}
