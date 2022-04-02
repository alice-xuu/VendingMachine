package vendingMachine.model;

public class PasswordVerificationMismatchException extends Exception {
    public PasswordVerificationMismatchException(String msg) {
        super(msg);
    }
}
