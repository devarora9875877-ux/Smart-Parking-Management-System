public class InputValidator {

    public boolean isValidVehicleNumber(String vehicleNumber) {

        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean isValidHours(int hours) {

        if (hours <= 0) {
            return false;
        }

        return true;
    }

    public boolean isValidVehicleType(String vehicleType) {

        if (vehicleType.equalsIgnoreCase("Car") ||
            vehicleType.equalsIgnoreCase("Bike")) {
            return true;
        }

        return false;
    }
}
