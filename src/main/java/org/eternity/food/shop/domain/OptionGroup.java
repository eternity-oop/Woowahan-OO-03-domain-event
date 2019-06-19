package org.eternity.food.shop.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class OptionGroup {
    private String name;
    private List<Option> options;

    @Builder
    public OptionGroup(String name, List<Option> options) {
        this.name = name;
        this.options = options;
    }
}
