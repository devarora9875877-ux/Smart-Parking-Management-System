import java.time.LocalDateTime;
import java.util.ArrayList;

public class ParkingManager {
    private ParkingLot parkingLot;
    private ArrayList<ParkingTicket> activeTickets;
    private int ticketCounter;

    public ParkingManager(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        activeTickets = new ArrayList<ParkingTicket>();
        ticketCounter = 1;
    }

    public ParkingTicket parkVehicle(Vehicle vehicle) {
        if (vehicle == null || isBlank(vehicle.getRegistrationNumber())
                || isBlank(vehicle.getBrand()) || isBlank(vehicle.getVehicleName())) {
            return null;
        }

        String vehicleType = vehicle.getVehicleType();
        if (!"Car".equalsIgnoreCase(vehicleType) && !"Bike".equalsIgnoreCase(vehicleType)) {
            return null;
        }

        if (searchParkedVehicle(vehicle.getRegistrationNumber()) != null) {
            return null;
        }

        ParkingSlot slot = parkingLot.findAvailableSlot(vehicleType);
        if (slot == null || !slot.parkVehicle(vehicle)) {
            return null;
        }

        String ticketId = "T" + ticketCounter;
        ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, slot, LocalDateTime.now());
        activeTickets.add(ticket);
        ticketCounter++;
        return ticket;
    }

    public ParkingTicket exitVehicle(String registrationNumber) {
        int ticketIndex = findTicketIndex(registrationNumber);
        if (ticketIndex == -1) {
            return null;
        }

        ParkingTicket ticket = activeTickets.get(ticketIndex);
        ticket.setExitTime(LocalDateTime.now());
        ticket.getParkingSlot().removeVehicle();
        activeTickets.remove(ticketIndex);
        return ticket;
    }

    public ParkingTicket searchParkedVehicle(String registrationNumber) {
        int ticketIndex = findTicketIndex(registrationNumber);
        if (ticketIndex == -1) {
            return null;
        }
        return activeTickets.get(ticketIndex);
    }

    private int findTicketIndex(String registrationNumber) {
        if (isBlank(registrationNumber)) {
            return -1;
        }

        String searchNumber = registrationNumber.trim();
        for (int i = 0; i < activeTickets.size(); i++) {
            Vehicle vehicle = activeTickets.get(i).getVehicle();
            if (vehicle.getRegistrationNumber().trim().equalsIgnoreCase(searchNumber)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }

    public void displayActiveTickets() {
        if (activeTickets.isEmpty()) {
            System.out.println("No active parking tickets.");
        }
        for (int i = 0; i < activeTickets.size(); i++) {
            activeTickets.get(i).displayTicketDetails();
        }
    }

    public void displayParkingStatus() {
        parkingLot.displayParkingStatus();
    }
}
