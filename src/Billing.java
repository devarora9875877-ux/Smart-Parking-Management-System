public class Billing {

    private String vehicleNumber;
    private int parkingHours;
    private double totalAmount;

    public Billing(String vehicleNumber, int parkingHours) {
        this.vehicleNumber = vehicleNumber;
        this.parkingHours = parkingHours;
    }

    public void generateBill(String vehicleType) {

        RateCalculator calculator = new RateCalculator();

        totalAmount = calculator.calculateRate(vehicleType, parkingHours);

        System.out.println("----- PARKING BILL -----");
        System.out.println("Vehicle Number: " + vehicleNumber);
        System.out.println("Parking Hours: " + parkingHours);
        System.out.println("Total Amount: Rs. " + totalAmount);
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
