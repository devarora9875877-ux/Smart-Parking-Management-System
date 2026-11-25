import java.util.ArrayList;

public class ParkingLot {
    private String parkingLotName;
    private ArrayList<ParkingFloor> floors;

    public ParkingLot(String parkingLotName) {
        this.parkingLotName = parkingLotName;
        floors = new ArrayList<ParkingFloor>();
    }

    public boolean addFloor(ParkingFloor floor) {
        if (floor == null) {
            return false;
        }

        for (int i = 0; i < floors.size(); i++) {
            if (floors.get(i).getFloorNumber() == floor.getFloorNumber()) {
                return false;
            }
        }

        floors.add(floor);
        return true;
    }

    public ArrayList<ParkingFloor> getFloors() {
        // A copy keeps callers from bypassing addFloor's duplicate check.
        return new ArrayList<ParkingFloor>(floors);
    }

    public ParkingSlot findAvailableSlot(String vehicleType) {
        for (int i = 0; i < floors.size(); i++) {
            ParkingSlot slot = floors.get(i).findAvailableSlot(vehicleType);
            if (slot != null) {
                return slot;
            }
        }
        return null;
    }

    public int getTotalSlots() {
        return getAvailableSlots() + getOccupiedSlots();
    }

    public int getAvailableSlots() {
        int availableCount = 0;
        for (int i = 0; i < floors.size(); i++) {
            availableCount += floors.get(i).getAvailableSlotCount();
        }
        return availableCount;
    }

    public int getOccupiedSlots() {
        int occupiedCount = 0;
        for (int i = 0; i < floors.size(); i++) {
            occupiedCount += floors.get(i).getOccupiedSlotCount();
        }
        return occupiedCount;
    }

    public void displayParkingStatus() {
        System.out.println("\nParking lot: " + parkingLotName);
        for (int i = 0; i < floors.size(); i++) {
            floors.get(i).displayFloorDetails();
        }
        System.out.println("\nTotal slots: " + getTotalSlots());
        System.out.println("Available slots: " + getAvailableSlots());
        System.out.println("Occupied slots: " + getOccupiedSlots());
    }
}
