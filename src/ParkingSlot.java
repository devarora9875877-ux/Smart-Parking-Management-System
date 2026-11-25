public class ParkingSlot {
    private String slotId;
    private String slotType;
    private Vehicle parkedVehicle;

    public ParkingSlot(String slotId, String slotType) {
        this.slotId = slotId;
        this.slotType = slotType;
        this.parkedVehicle = null;
    }

    public String getSlotId() {
        return slotId;
    }

    public String getSlotType() {
        return slotType;
    }

    public boolean isAvailable() {
        return parkedVehicle == null;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public boolean parkVehicle(Vehicle vehicle) {
        if (vehicle == null || !isAvailable()) {
            return false;
        }
        if (!slotType.equalsIgnoreCase(vehicle.getVehicleType())) {
            return false;
        }

        parkedVehicle = vehicle;
        return true;
    }

    public void removeVehicle() {
        parkedVehicle = null;
    }

    public void displaySlotDetails() {
        if (isAvailable()) {
            System.out.println(slotId + " (" + slotType + ") - Available");
        } else {
            System.out.println(slotId + " (" + slotType + ") - Occupied by "
                    + parkedVehicle.getRegistrationNumber());
        }
    }
}
