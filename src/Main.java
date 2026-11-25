public class Main {

    public static void main(String[] args) {

        VehicleRegistry registry = new VehicleRegistry();
        Admin admin = new Admin(1, "ParkingAdmin");

        Vehicle car = new Car("PB01AB1234", "Toyota", "Fortuner");
        Vehicle bike = new Bike("PB01XY5678", "Royal Enfield", "Classic 350");

        Customer customer1 = new Customer(101, "Dev", "9876543210", car);
        Customer customer2 = new Customer(102, "Rahul", "9876543211", bike);

        System.out.println("--- Registering Vehicles ---");
        admin.registerVehicle(registry, car);
        admin.registerVehicle(registry, bike);

        System.out.println("\n--- Registering Customers ---");
        admin.registerCustomer(registry, customer1);
        admin.registerCustomer(registry, customer2);

        System.out.println("\n--- All Vehicles ---");
        admin.displayVehicles(registry);

        System.out.println("\n--- All Customers ---");
        admin.displayCustomers(registry);

        System.out.println("\n--- Searching for a Vehicle ---");
        admin.searchVehicle(registry, "PB01AB1234");

        System.out.println("\n--- Testing Duplicate Registration ---");
        Vehicle duplicateCar = new Car("PB01AB1234", "Honda", "City");
        admin.registerVehicle(registry, duplicateCar);

        System.out.println("\n--- Removing a Vehicle ---");
        admin.removeVehicle(registry, "PB01XY5678");

        System.out.println("\n--- Vehicles After Removal ---");
        admin.displayVehicles(registry);

        // PARKING LOT, FLOORS, SLOTS, TICKETS AND MANAGER
        System.out.println("\n--- Initializing Parking Lot ---");
        ParkingLot parkingLot = new ParkingLot("Smart Parking Lot");
        parkingLot.addFloor(new ParkingFloor(1));
        parkingLot.addFloor(new ParkingFloor(2));
        ParkingManager parkingManager = new ParkingManager(parkingLot);

        System.out.println("\n--- Parking Registered Vehicle ---");
        Vehicle parkedCar = registry.searchVehicle("PB01AB1234");
        ParkingTicket ticket = parkingManager.parkVehicle(parkedCar);
        if (ticket != null) {
            System.out.println("Vehicle parked successfully.");
            ticket.displayTicketDetails();
        } else {
            System.out.println("Unable to park vehicle.");
        }

        System.out.println("\n--- Active Parking Tickets ---");
        parkingManager.displayActiveTickets();

        System.out.println("\n--- Searching Parked Vehicle ---");
        ParkingTicket foundTicket = parkingManager.searchParkedVehicle("PB01AB1234");
        if (foundTicket != null) {
            foundTicket.displayTicketDetails();
        } else {
            System.out.println("Vehicle is not parked.");
        }

        System.out.println("\n--- Parking Lot Status (Occupied) ---");
        parkingManager.displayParkingStatus();

        System.out.println("\n--- Exiting Parked Vehicle ---");
        ParkingTicket completedTicket = parkingManager.exitVehicle("PB01AB1234");
        if (completedTicket != null) {
            System.out.println("Vehicle exited successfully.");
            completedTicket.displayTicketDetails();
        } else {
            System.out.println("Vehicle is not parked.");
        }

        System.out.println("\n--- Parking Lot Status (After Exit) ---");
        parkingManager.displayParkingStatus();

        // BILLING, VALIDATION, PAYMENT AND HISTORY
        try {
            InputValidator validator = new InputValidator();
            ParkingHistory history = new ParkingHistory();

            String vehicleNumber = (completedTicket != null)
                    ? completedTicket.getVehicle().getRegistrationNumber()
                    : "PB01AB1234";
            String vehicleType = (completedTicket != null)
                    ? completedTicket.getVehicle().getVehicleType()
                    : "Car";
            int hours = (completedTicket != null && completedTicket.getParkingDurationMinutes() > 0)
                    ? (int) Math.ceil(completedTicket.getParkingDurationMinutes() / 60.0)
                    : 3;

            // Input Validation
            if (!validator.isValidVehicleNumber(vehicleNumber)) {
                throw new ParkingException("Invalid vehicle number!");
            }

            if (!validator.isValidVehicleType(vehicleType)) {
                throw new ParkingException("Invalid vehicle type!");
            }

            if (!validator.isValidHours(hours)) {
                throw new ParkingException("Invalid parking hours!");
            }

            System.out.println("\n--- Billing ---");

            // Billing and Rate Calculation
            Billing bill = new Billing(vehicleNumber, hours);
            bill.generateBill(vehicleType);

            double amount = bill.getTotalAmount();

            // Payment
            System.out.println("\n--- Payment ---");
            Payment payment = new Payment(amount, "UPI");
            payment.makePayment();
            payment.displayPayment();

            // Parking History
            System.out.println("\n--- Parking History ---");
            history.addRecord(vehicleNumber, vehicleType, hours, amount);
            history.displayHistory();

        } catch (ParkingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
