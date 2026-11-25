import java.util.ArrayList;

public class ParkingHistory {

    private ArrayList<String> records;

    public ParkingHistory() {
        records = new ArrayList<>();
    }

    public void addRecord(String vehicleNumber, String vehicleType,
                          int hours, double amount) {

        String record = "Vehicle: " + vehicleNumber
                + ", Type: " + vehicleType
                + ", Hours: " + hours
                + ", Amount: Rs. " + amount;

        records.add(record);

        System.out.println("Parking record added successfully!");
    }

    public void displayHistory() {

        System.out.println("----- PARKING HISTORY -----");

        if (records.isEmpty()) {
            System.out.println("No parking records found.");
        }
        else {
            for (String record : records) {
                System.out.println(record);
            }
        }
    }
}
