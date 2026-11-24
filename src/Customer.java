public class Customer {
    private int customerId;
    private String name;
    private String phoneNumber;
    private Vehicle vehicle;
    public Customer(int customerId, String name, String phoneNumber, Vehicle vehicle) {
        this.customerId = customerId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.vehicle = vehicle;
    }
    public int getCustomerId() {
        return customerId;
    }
    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void displayCustomerDetails() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Phone Number: " + phoneNumber);

        System.out.println("Vehicle Details:");
        vehicle.displayDetails();
    }
}
