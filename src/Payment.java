public class Payment {

    private double amount;
    private String paymentMethod;
    private String paymentStatus;

    public Payment(double amount, String paymentMethod) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = "Pending";
    }

    public void makePayment() {

        paymentStatus = "Successful";

        System.out.println("Payment Successful!");
        System.out.println("Amount Paid: Rs. " + amount);
        System.out.println("Payment Method: " + paymentMethod);
    }

    public void displayPayment() {

        System.out.println("Payment Amount: Rs. " + amount);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Payment Status: " + paymentStatus);
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}
