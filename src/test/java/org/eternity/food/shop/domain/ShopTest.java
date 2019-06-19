package org.eternity.food.shop.domain;

import org.eternity.food.generic.money.domain.Money;
import org.eternity.food.generic.money.domain.Ratio;
import org.eternity.food.shop.domain.Shop;
import org.junit.Test;

import static org.eternity.food.Fixtures.aShop;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ShopTest {
    @Test
    public void 최소주문금액_체크() {
        Shop shop = aShop().minOrderAmount(Money.wons(15000)).build();

        assertThat(shop.isValidOrderAmount(Money.wons(14000)), is(false));
        assertThat(shop.isValidOrderAmount(Money.wons(15000)), is(true));
        assertThat(shop.isValidOrderAmount(Money.wons(16000)), is(true));
    }

    @Test
    public void 수수료_계산() {
        Shop shop = aShop()
                        .commissionRate(Ratio.valueOf(0.1))
                        .build();

        assertThat(shop.calculateCommissionFee(Money.wons(1000)), is(Money.wons(100)));
    }
}
