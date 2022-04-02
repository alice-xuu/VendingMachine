package vendingMachine.model;

public class NoAvailableChangeException extends Exception{
    public NoAvailableChangeException(String msg) {
        super(msg);
    }
}
