package org.eternity.food.delivery.domain;

import org.eternity.food.order.domain.OrderDeliveredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CompleteDeliveryWithOrderDeliveredEventHandler {
    private DeliveryRepository deliveryRepository;

    public CompleteDeliveryWithOrderDeliveredEventHandler(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Async
    @EventListener
    @Transactional
    public void handle(OrderDeliveredEvent event) {
        Delivery delivery = deliveryRepository.findById(event.getOrderId()).orElseThrow(IllegalArgumentException::new);
        delivery.complete();
    }
}
