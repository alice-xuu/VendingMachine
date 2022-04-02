package vendingMachine.model;

public class UsernameAlreadyExistsException extends Exception {
    public UsernameAlreadyExistsException(String msg) {
        super(msg);
    }
}
