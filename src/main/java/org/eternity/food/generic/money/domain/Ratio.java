package org.eternity.food.generic.money.domain;

public class Ratio {
    private double rate;

    public static Ratio valueOf(double rate) {
        return new Ratio(rate);
    }

    Ratio(double rate) {
        this.rate = rate;
    }

    Ratio() {
    }

    public Money of(Money price) {
        return price.times(rate);
    }

    public double getRate() {
        return rate;
    }
}
