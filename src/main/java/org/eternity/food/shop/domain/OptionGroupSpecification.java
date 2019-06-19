package org.eternity.food.shop.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name="OPTION_GROUP_SPECS")
@Getter
public class OptionGroupSpecification {
    @Id
    @GeneratedValue
    @Column(name="OPTION_GROUP_SPEC_ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name="EXCLUSIVE")
    private boolean exclusive;

    @Column(name="BASIC")
    private boolean basic;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="OPTION_GROUP_SPEC_ID")
    private List<OptionSpecification> optionSpecs = new ArrayList<>();

    public static OptionGroupSpecification basic(String name, boolean exclusive, OptionSpecification... options) {
        return new OptionGroupSpecification(name, exclusive, true, options);
    }

    public static OptionGroupSpecification additive(String name, boolean exclusive, OptionSpecification... options) {
        return new OptionGroupSpecification(name, exclusive, false, options);
    }

    public OptionGroupSpecification(String name, boolean exclusive, boolean basic, OptionSpecification... options) {
        this(null, name, exclusive, basic, Arrays.asList(options));
    }

    @Builder
    public OptionGroupSpecification(Long id, String name, boolean exclusive, boolean basic, List<OptionSpecification> options) {
        this.id = id;
        this.name = name;
        this.exclusive = exclusive;
        this.basic = basic;
        this.optionSpecs.addAll(options);
    }

    OptionGroupSpecification() {
    }

    public String getName() {
        return name;
    }

    public boolean isSatisfiedBy(OptionGroup optionGroup) {
        return !isSatisfied(optionGroup.getName(), satisfied(optionGroup.getOptions()));
    }

    private boolean isSatisfied(String groupName, List<Option> satisfied) {
        if (!name.equals(groupName)) {
            return false;
        }

        if (satisfied.isEmpty()) {
            return false;
        }

        if (exclusive && satisfied.size() > 1) {
            return false;
        }

        return true;
    }

    private List<Option> satisfied(List<Option> options) {
        return optionSpecs
                .stream()
                .flatMap(spec -> options.stream().filter(spec::isSatisfiedBy))
                .collect(toList());
    }
}
