import java.time.Duration;
import java.time.LocalDateTime;

public class ParkingTicket {
    private String ticketId;
    private Vehicle vehicle;
    private ParkingSlot parkingSlot;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public ParkingTicket(String ticketId, Vehicle vehicle, ParkingSlot parkingSlot,
                         LocalDateTime entryTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.parkingSlot = parkingSlot;
        this.entryTime = entryTime;
        this.exitTime = null;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public long getParkingDurationMinutes() {
        LocalDateTime endTime = exitTime;
        if (endTime == null) {
            endTime = LocalDateTime.now();
        }
        return Duration.between(entryTime, endTime).toMinutes();
    }

    public void displayTicketDetails() {
        System.out.println("\nTicket ID: " + ticketId);
        vehicle.displayDetails();
        System.out.println("Parking slot: " + parkingSlot.getSlotId());
        System.out.println("Entry time: " + entryTime);
        if (exitTime == null) {
            System.out.println("Exit time: Vehicle still parked");
        } else {
            System.out.println("Exit time: " + exitTime);
        }
        System.out.println("Parking duration: " + getParkingDurationMinutes() + " minute(s)");
    }
}
