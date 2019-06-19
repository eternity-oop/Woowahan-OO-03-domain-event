package org.eternity.food.order.domain;

import lombok.Builder;
import lombok.Getter;
import org.eternity.food.generic.money.domain.Money;
import org.eternity.food.shop.domain.Option;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class OrderOption {
    @Column(name="NAME")
    private String name;

    @Column(name="PRICE")
    private Money price;

    @Builder
    public OrderOption(String name, Money price) {
        this.name = name;
        this.price = price;
    }

    OrderOption() {
    }

    public Option convertToOption() {
        return new Option(name, price);
    }
}
