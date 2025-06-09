package com.example.model;

import com.example.model.pizza.Pizza;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Order {
    @JsonProperty("pizzas")
    private List<Pizza> pizzas;

    public Order(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }
}
