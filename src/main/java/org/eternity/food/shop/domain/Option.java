package org.eternity.food.shop.domain;

import lombok.Builder;
import lombok.Data;
import org.eternity.food.generic.money.domain.Money;

@Data
public class Option {
    private String name;
    private Money price;

    @Builder
    public Option(String name, Money price) {
        this.name = name;
        this.price = price;
    }
}
