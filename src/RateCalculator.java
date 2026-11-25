public class RateCalculator {

    public double calculateRate(String vehicleType, int hours) {

        double rate = 0;

        if (vehicleType.equalsIgnoreCase("Car")) {
            rate = 100;
        }
        else if (vehicleType.equalsIgnoreCase("Bike")) {
            rate = 50;
        }

        return rate * hours;
    }
}
