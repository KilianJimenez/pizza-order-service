package com.example.model.pizza;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Pizza {
    @JsonProperty("name")
    private Name name;

    @JsonProperty("size")
    private Size size;

    @JsonCreator
    public Pizza(@JsonProperty("name") Name name,@JsonProperty("size") Size size) {
        this.name = name;
        this.size = size;
    }
}
