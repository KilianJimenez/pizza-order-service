package com.example.model.pizza;

public enum Size {
    M(0),
    L(2);

    private final double extraPrice;

    Size(double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public double getExtraPrice() {
        return extraPrice;
    }
}
