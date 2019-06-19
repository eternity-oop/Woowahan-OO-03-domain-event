package org.eternity.food.order.domain;

import lombok.Builder;
import lombok.Getter;
import org.eternity.food.generic.money.domain.Money;
import org.eternity.food.shop.domain.OptionGroup;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name="ORDER_LINE_ITEMS")
@Getter
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ORDER_LINE_ITEM_ID")
    private Long id;

    @Column(name="MENU_ID")
    private Long menuId;

    @Column(name="FOOD_NAME")
    private String name;

    @Column(name="FOOD_COUNT")
    private int count;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="ORDER_LINE_ITEM_ID")
    private List<OrderOptionGroup> groups = new ArrayList<>();

    public OrderLineItem(Long menuId, String name, int count, List<OrderOptionGroup> groups) {
        this(null, menuId, name, count, groups);
    }

    @Builder
    public OrderLineItem(Long id, Long menuId, String name, int count, List<OrderOptionGroup> groups) {
        this.id = id;
        this.menuId = menuId;
        this.name = name;
        this.count = count;
        this.groups.addAll(groups);
    }

    OrderLineItem() {
    }

    public Money calculatePrice() {
        return Money.sum(groups, OrderOptionGroup::calculatePrice).times(count);
    }

    private List<OptionGroup> convertToOptionGroups() {
        return groups.stream().map(OrderOptionGroup::convertToOptionGroup).collect(toList());
    }
}
