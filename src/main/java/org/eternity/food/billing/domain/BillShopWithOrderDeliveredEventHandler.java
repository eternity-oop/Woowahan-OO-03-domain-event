package org.eternity.food.billing.domain;

import org.eternity.food.order.domain.OrderDeliveredEvent;
import org.eternity.food.shop.domain.Shop;
import org.eternity.food.shop.domain.ShopRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BillShopWithOrderDeliveredEventHandler {
    private ShopRepository shopRepository;
    private BillingRepository billingRepository;

    public BillShopWithOrderDeliveredEventHandler(ShopRepository shopRepository, BillingRepository billingRepository) {
        this.shopRepository = shopRepository;
        this.billingRepository = billingRepository;
    }

    @Async
    @EventListener
    @Transactional
    public void handle(OrderDeliveredEvent event) {
        Shop shop = shopRepository.findById(event.getShopId())
                                  .orElseThrow(IllegalArgumentException::new);
        Billing billing = billingRepository.findByShopId(event.getShopId())
                                  .orElse(new Billing(event.getShopId()));

        billing.billCommissionFee(shop.calculateCommissionFee(event.getTotalPrice()));
    }
}
