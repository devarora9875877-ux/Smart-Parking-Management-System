public class ParkingFloor {
    private int floorNumber;
    private ParkingSlot[][] slots;

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        slots = new ParkingSlot[2][3];
        int slotNumber = 1;

        for (int row = 0; row < slots.length; row++) {
            String slotType;
            if (row == 0) {
                slotType = "Car";
            } else {
                slotType = "Bike";
            }

            for (int column = 0; column < slots[row].length; column++) {
                String slotId = "F" + floorNumber + "-0" + slotNumber;
                slots[row][column] = new ParkingSlot(slotId, slotType);
                slotNumber++;
            }
        }
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public ParkingSlot[][] getSlots() {
        return slots;
    }

    public ParkingSlot findAvailableSlot(String vehicleType) {
        for (int row = 0; row < slots.length; row++) {
            for (int column = 0; column < slots[row].length; column++) {
                ParkingSlot slot = slots[row][column];
                if (slot.isAvailable() && slot.getSlotType().equalsIgnoreCase(vehicleType)) {
                    return slot;
                }
            }
        }
        return null;
    }

    public int getAvailableSlotCount() {
        int availableCount = 0;
        for (int row = 0; row < slots.length; row++) {
            for (int column = 0; column < slots[row].length; column++) {
                if (slots[row][column].isAvailable()) {
                    availableCount++;
                }
            }
        }
        return availableCount;
    }

    public int getOccupiedSlotCount() {
        int totalCount = slots.length * slots[0].length;
        return totalCount - getAvailableSlotCount();
    }

    public void displayFloorDetails() {
        System.out.println("\nFloor " + floorNumber);
        for (int row = 0; row < slots.length; row++) {
            for (int column = 0; column < slots[row].length; column++) {
                slots[row][column].displaySlotDetails();
            }
        }
    }
}
