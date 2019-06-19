package org.eternity.food.billing.domain;

import lombok.Builder;
import lombok.Getter;
import org.eternity.food.generic.money.domain.Money;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="BILLINGS")
@Getter
public class Billing {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="BILLING_ID")
    private Long id;

    @Column(name="SHOP_ID")
    private Long shopId;

    @Column(name = "COMMISSION")
    private Money commission = Money.ZERO;

    public Billing(Long shopId) {
        this(null, shopId, Money.ZERO);
    }

    @Builder
    public Billing(Long id, Long shopId, Money commission) {
        this.id = id;
        this.shopId = shopId;
        this.commission = commission;
    }

    Billing() {
    }

    public void billCommissionFee(Money commission) {
        this.commission = this.commission.plus(commission);
    }
}

