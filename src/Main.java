import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        VehicleRegistry registry = new VehicleRegistry();
        Admin admin = new Admin(1, "ParkingAdmin");

        Scanner sc = new Scanner(System.in);

        int choice;

        do {

            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Register Vehicle");
            System.out.println("2. Register Customer");
            System.out.println("3. Display Vehicles");
            System.out.println("4. Display Customers");
            System.out.println("5. Search Vehicle");
            System.out.println("6. Remove Vehicle");
            System.out.println("7. Continue to Parking Module");
            System.out.print("Enter Choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter Vehicle Type (Car/Bike): ");
                    String type = sc.nextLine();

                    System.out.print("Enter Registration Number: ");
                    String regNo = sc.nextLine();

                    System.out.print("Enter Brand: ");
                    String brand = sc.nextLine();

                    System.out.print("Enter Vehicle Name: ");
                    String vehicleName = sc.nextLine();

                    Vehicle vehicle;

                    if (type.equalsIgnoreCase("Car")) {
                        vehicle = new Car(regNo, brand, vehicleName);
                    } else if (type.equalsIgnoreCase("Bike")) {
                        vehicle = new Bike(regNo, brand, vehicleName);
                    } else {
                        System.out.println("Invalid Vehicle Type.");
                        break;
                    }

                    admin.registerVehicle(registry, vehicle);
                    break;

                case 2:

                    System.out.print("Enter Customer ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Customer Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Phone Number: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter Registered Vehicle Number: ");
                    String vehicleNo = sc.nextLine();

                    Vehicle customerVehicle = registry.searchVehicle(vehicleNo);

                    if (customerVehicle == null) {
                        System.out.println("Vehicle not found. Register vehicle first.");
                    } else {

                        Customer customer = new Customer(id, name, phone, customerVehicle);

                        admin.registerCustomer(registry, customer);
                    }

                    break;

                case 3:

                    admin.displayVehicles(registry);

                    break;

                case 4:

                    admin.displayCustomers(registry);

                    break;

                case 5:

                    System.out.print("Enter Registration Number: ");
                    String search = sc.nextLine();

                    admin.searchVehicle(registry, search);

                    break;

                case 6:

                    System.out.print("Enter Registration Number: ");
                    String remove = sc.nextLine();

                    admin.removeVehicle(registry, remove);

                    break;

                case 7:

                    System.out.println("Continuing to Parking Module...");

                    break;

                default:

                    System.out.println("Invalid Choice.");

            }

        } while (choice != 7);

        System.out.println("\n--- Initializing Parking Lot ---");

        ParkingLot parkingLot = new ParkingLot("Smart Parking Lot");
        parkingLot.addFloor(new ParkingFloor(1));
        parkingLot.addFloor(new ParkingFloor(2));

        ParkingManager parkingManager = new ParkingManager(parkingLot);
        ParkingHistory history = new ParkingHistory();

        int parkingChoice;

        do {

            System.out.println("\n===== PARKING MENU =====");
            System.out.println("1. Park Vehicle");
            System.out.println("2. Search Parked Vehicle");
            System.out.println("3. Exit Vehicle");
            System.out.println("4. Display Active Tickets");
            System.out.println("5. Display Parking Status");
            System.out.println("6. Display Parking History");
            System.out.println("7. Back");
            System.out.print("Enter Choice: ");

            parkingChoice = sc.nextInt();
            sc.nextLine();

            switch (parkingChoice) {

                case 1:

                    System.out.print("Enter Vehicle Registration Number: ");
                    String parkNo = sc.nextLine();

                    Vehicle vehicle = registry.searchVehicle(parkNo);

                    if (vehicle == null) {

                        System.out.println("Vehicle not registered.");

                    } else {

                        ParkingTicket ticket = parkingManager.parkVehicle(vehicle);

                        if (ticket != null) {

                            System.out.println("Vehicle parked successfully.");

                            ticket.displayTicketDetails();

                        } else {

                            System.out.println("Parking Failed.");

                        }

                    }

                    break;

                case 2:

                    System.out.print("Enter Registration Number: ");
                    String searchNo = sc.nextLine();

                    ParkingTicket foundTicket = parkingManager.searchParkedVehicle(searchNo);

                    if (foundTicket != null) {

                        foundTicket.displayTicketDetails();

                    } else {

                        System.out.println("Vehicle not parked.");

                    }

                    break;

                case 3:

                    System.out.print("Enter Registration Number: ");
                    String exitNo = sc.nextLine();

                    ParkingTicket completedTicket = parkingManager.exitVehicle(exitNo);

                    if (completedTicket != null) {

                        completedTicket.displayTicketDetails();

                        try {

                            InputValidator validator = new InputValidator();

                            String vehicleNumber = completedTicket.getVehicle().getRegistrationNumber();

                            String vehicleType = completedTicket.getVehicle().getVehicleType();

                            int hours = (int) Math.ceil(completedTicket.getParkingDurationMinutes() / 60.0);

                            if (hours <= 0)
                                hours = 1;

                            if (!validator.isValidVehicleNumber(vehicleNumber))
                                throw new ParkingException("Invalid Vehicle Number");

                            if (!validator.isValidVehicleType(vehicleType))
                                throw new ParkingException("Invalid Vehicle Type");

                            if (!validator.isValidHours(hours))
                                throw new ParkingException("Invalid Hours");

                            Billing bill = new Billing(vehicleNumber, hours);

                            bill.generateBill(vehicleType);

                            double amount = bill.getTotalAmount();

                            System.out.print("Enter Payment Method (UPI/Cash/Card): ");
                            String method = sc.nextLine();

                            Payment payment = new Payment(amount, method);

                            payment.makePayment();

                            payment.displayPayment();

                            history.addRecord(vehicleNumber,
                                    vehicleType,
                                    hours,
                                    amount);

                        }

                        catch (ParkingException e) {

                            System.out.println(e.getMessage());

                        }

                    }

                    else {

                        System.out.println("Vehicle not parked.");

                    }

                    break;

                case 4:

                    parkingManager.displayActiveTickets();

                    break;

                case 5:

                    parkingManager.displayParkingStatus();

                    break;

                case 6:

                    history.displayHistory();

                    break;

                case 7:

                    System.out.println("Returning...");

                    break;

                default:

                    System.out.println("Invalid Choice.");

            }

        } while (parkingChoice != 7);

        sc.close();
    }
}