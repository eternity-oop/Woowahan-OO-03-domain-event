package org.eternity.food.order.domain;

import lombok.Builder;
import lombok.Getter;
import org.eternity.food.generic.money.domain.Money;
import org.eternity.food.shop.domain.OptionGroup;

import javax.persistence.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name="ORDER_OPTION_GROUPS")
@Getter
public class OrderOptionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ORDER_OPTION_GROUP_ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    @ElementCollection
    @CollectionTable(name="ORDER_OPTIONS", joinColumns = @JoinColumn(name="ORDER_OPTION_GROUP_ID"))
    private List<OrderOption> orderOptions;

    public OrderOptionGroup(String name, List<OrderOption> options) {
        this(null, name, options);
    }

    @Builder
    public OrderOptionGroup(Long id, String name, List<OrderOption> options) {
        this.id = id;
        this.name = name;
        this.orderOptions = options;
    }

    OrderOptionGroup() {
    }

    public Money calculatePrice() {
        return Money.sum(orderOptions, OrderOption::getPrice);
    }

    public OptionGroup convertToOptionGroup() {
        return new OptionGroup(name, orderOptions.stream().map(OrderOption::convertToOption).collect(toList()));
    }
}
