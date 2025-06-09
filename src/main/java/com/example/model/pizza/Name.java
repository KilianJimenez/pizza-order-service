package com.example.model.pizza;

public enum Name {
    DIAVOLA(6),
    FRUTTI_DI_MARE(7),
    MARGHERITTA(5);

    private final double basePrice;

    Name(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }
}
