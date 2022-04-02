package vendingMachine.model;

public class NotEnoughMoneyException extends Exception{
    public NotEnoughMoneyException(String msg) {
        super(msg);
    }
}
