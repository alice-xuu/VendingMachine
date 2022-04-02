package vendingMachine.model;

public class PasswordTooShortException extends Exception {
    public PasswordTooShortException(String msg) {
        super(msg);
    }
}
