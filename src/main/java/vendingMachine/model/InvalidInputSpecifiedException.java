package vendingMachine.model;

public class InvalidInputSpecifiedException extends Exception {
    public InvalidInputSpecifiedException(String msg) {
        super(msg);
    }
}
