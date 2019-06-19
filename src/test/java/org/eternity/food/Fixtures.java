package org.eternity.food;

import org.eternity.food.billing.domain.Billing;
import org.eternity.food.delivery.domain.Delivery;
import org.eternity.food.delivery.domain.Delivery.DeliveryBuilder;
import org.eternity.food.generic.money.domain.Money;
import org.eternity.food.generic.money.domain.Ratio;
import org.eternity.food.order.domain.Order;
import org.eternity.food.order.domain.Order.OrderBuilder;
import org.eternity.food.order.domain.OrderLineItem;
import org.eternity.food.order.domain.OrderLineItem.OrderLineItemBuilder;
import org.eternity.food.order.domain.OrderOption;
import org.eternity.food.order.domain.OrderOption.OrderOptionBuilder;
import org.eternity.food.order.domain.OrderOptionGroup;
import org.eternity.food.order.domain.OrderOptionGroup.OrderOptionGroupBuilder;
import org.eternity.food.shop.domain.*;
import org.eternity.food.shop.domain.Menu.MenuBuilder;
import org.eternity.food.shop.domain.Option.OptionBuilder;
import org.eternity.food.shop.domain.OptionGroup.OptionGroupBuilder;
import org.eternity.food.shop.domain.OptionGroupSpecification.OptionGroupSpecificationBuilder;
import org.eternity.food.shop.domain.OptionSpecification.OptionSpecificationBuilder;
import org.eternity.food.shop.domain.Shop.ShopBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Fixtures {
    public static ShopBuilder aShop() {
        return Shop.builder()
                .id(1L)
                .name("오겹돼지")
                .commissionRate(Ratio.valueOf(0.01))
                .open(true)
                .minOrderAmount(Money.wons(13000));
    }

    public static MenuBuilder aMenu() {
        return Menu.builder()
                .id(1L)
                .shopId(aShop().build().getId())
                .name("삼겹살 1인세트")
                .description("삼겹살 + 야채세트 + 김치찌개")
                .basic(anOptionGroupSpec()
                            .name("기본")
                            .options(Arrays.asList(anOptionSpec().name("소(250g)").price(Money.wons(12000)).build()))
                            .build())
                .additives(Arrays.asList(
                        anOptionGroupSpec()
                            .basic(false)
                            .name("맛선택")
                            .options(Arrays.asList(anOptionSpec().name("매콤 맛").price(Money.wons(1000)).build()))
                            .build()));
    }

    public static OptionGroupSpecificationBuilder anOptionGroupSpec() {
        return OptionGroupSpecification.builder()
                .basic(true)
                .exclusive(true)
                .name("기본")
                .options(Arrays.asList(anOptionSpec().build()));
    }

    public static OptionSpecificationBuilder anOptionSpec() {
        return OptionSpecification.builder()
                .name("소(250g)")
                .price(Money.wons(12000));
    }

    public static OptionGroupBuilder anOptionGroup() {
        return OptionGroup.builder()
                .name("기본")
                .options(Arrays.asList(anOption().build()));
    }

    public static OptionBuilder anOption() {
        return Option.builder()
                .name("소(250g)")
                .price(Money.wons(12000));
    }

    public static OrderBuilder anOrder() {
        return Order.builder()
                .id(1L)
                .userId(1L)
                .shopId(aShop().build().getId())
                .status(Order.OrderStatus.ORDERED)
                .orderedTime(LocalDateTime.of(2020, 1, 1, 12, 0))
                .items(Arrays.asList(anOrderLineItem().build()));
    }

    public static OrderLineItemBuilder anOrderLineItem() {
        return OrderLineItem.builder()
                .menuId(aMenu().build().getId())
                .name("삼겹살 1인세트")
                .count(1)
                .groups(Arrays.asList(
                    anOrderOptionGroup()
                            .name("기본")
                            .options(Arrays.asList(anOrderOption().name("소(250g)").price(Money.wons(12000)).build()))
                            .build(),
                    anOrderOptionGroup()
                            .name("맛선택")
                            .options(Arrays.asList(anOrderOption().name("매콤 맛").price(Money.wons(1000)).build()))
                            .build()));
    }

    public static OrderOptionGroupBuilder anOrderOptionGroup() {
        return OrderOptionGroup.builder()
                .name("기본")
                .options(Arrays.asList(anOrderOption().build()));
    }

    public static OrderOptionBuilder anOrderOption() {
        return OrderOption.builder()
                .name("소(250g)")
                .price(Money.wons(12000));
    }

    public static DeliveryBuilder aDelivery() {
        return Delivery.builder()
                .id(1L)
                .deliveryStatus(Delivery.DeliveryStatus.DELIVERING)
                .orderId(anOrder().build().getId());
    }

    public static Billing.BillingBuilder aBilling() {
        return Billing.builder()
                    .id(1L)
                    .shopId(aShop().build().getId())
                    .commission(Money.ZERO);
    }
}