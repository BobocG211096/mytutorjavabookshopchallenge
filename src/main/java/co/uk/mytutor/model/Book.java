package co.uk.mytutor.model;

public class Book {
    private Integer quantity;
    private Integer quantitySold = 0;

    public Book(Integer quantity) {
        this.quantity = quantity;
    }

    public Book(Integer quantity, Integer quantitySold) {
        this.quantity = quantity;
        this.quantitySold = quantitySold;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
    }
}
