package org.eternity.food.order.domain;

import org.eternity.food.shop.domain.*;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class OrderValidator {
    private ShopRepository shopRepository;
    private MenuRepository menuRepository;

    public OrderValidator(ShopRepository shopRepository,
                          MenuRepository menuRepository) {
        this.shopRepository = shopRepository;
        this.menuRepository = menuRepository;
    }

    public void validate(Order order) {
        validate(order, getShop(order), getMenus(order));
    }

    void validate(Order order, Shop shop, Map<Long, Menu> menus) {
        if (!shop.isOpen()) {
            throw new IllegalArgumentException("가게가 영업중이 아닙니다.");
        }

        if (order.getOrderLineItems().isEmpty()) {
            throw new IllegalStateException("주문 항목이 비어 있습니다.");
        }

        if (!shop.isValidOrderAmount(order.calculateTotalPrice())) {
            throw new IllegalStateException(String.format("최소 주문 금액 %s 이상을 주문해주세요.", shop.getMinOrderAmount()));
        }

        for (OrderLineItem item : order.getOrderLineItems()) {
            validateOrderLineItem(item, menus.get(item.getMenuId()));
        }
    }

    private void validateOrderLineItem(OrderLineItem item, Menu menu) {
        if (!menu.getName().equals(item.getName())) {
            throw new IllegalArgumentException("기본 상품이 변경됐습니다.");
        }

        for(OrderOptionGroup group : item.getGroups()) {
            validateOrderOptionGroup(group, menu);
        }
    }

    private void validateOrderOptionGroup(OrderOptionGroup group, Menu menu) {
        for(OptionGroupSpecification spec : menu.getOptionGroupSpecs()) {
            if (spec.isSatisfiedBy(group.convertToOptionGroup())) {
                return;
            }
        }

        throw new IllegalArgumentException("메뉴가 변경됐습니다.");
    }

    private Shop getShop(Order order) {
        return shopRepository.findById(order.getShopId()).orElseThrow(IllegalArgumentException::new);
    }

    private Map<Long, Menu> getMenus(Order order) {
        return menuRepository.findAllById(order.getMenuIds()).stream().collect(toMap(Menu::getId, identity()));
    }
}