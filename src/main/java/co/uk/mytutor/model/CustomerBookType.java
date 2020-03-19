package co.uk.mytutor.model;

public enum CustomerBookType {
    A(25),B(20),C(23),D(30),E(27);

    private final Integer price;

    CustomerBookType(Integer price) {
        this.price = price;
    }

    public Integer getPrice(){return this.price;}
}
