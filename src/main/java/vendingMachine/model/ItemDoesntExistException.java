package vendingMachine.model;

public class ItemDoesntExistException extends Exception {
    public ItemDoesntExistException(String msg) {
        super(msg);
    }
}
