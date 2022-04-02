package vendingMachine.model;

public class Snack {

    private int code;
    private double cost;
    private int shoppingBasket = 0;
    private String category;
    private String name;

    /* Setter methods */
    public void setCost(double cost){
        this.cost = cost;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShoppingBasket(int shoppingBasket) {
        this.shoppingBasket = shoppingBasket;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /* Getter methods */
    public double getCost(){
        return this.cost;
    }

    public String getName(){
        return this.name;
    }

    public String getCategory(){
        return this.category;
    }

    public int getShoppingBasket() {
        return this.shoppingBasket;
    }

    public int getCode() {
        return this.code;
    }
}
