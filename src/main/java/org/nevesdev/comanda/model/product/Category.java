package org.nevesdev.comanda.model.product;

public enum Category {
    BEVERAGE("Bebida"),
    FOOD("Comida");

    private final String category;

    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

}
