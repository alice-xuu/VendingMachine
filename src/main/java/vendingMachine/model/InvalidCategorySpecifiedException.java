package vendingMachine.model;

public class InvalidCategorySpecifiedException extends Exception {
    public InvalidCategorySpecifiedException(String msg) {
        super(msg);
    }
}
