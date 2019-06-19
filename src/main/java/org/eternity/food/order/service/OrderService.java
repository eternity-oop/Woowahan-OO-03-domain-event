package org.eternity.food.order.service;

import org.eternity.food.order.domain.Order;
import org.eternity.food.order.domain.OrderRepository;
import org.eternity.food.order.domain.OrderValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private OrderValidator orderValidator;
    private OrderMapper orderMapper;

    public OrderService(OrderMapper orderMapper,
                        OrderRepository orderRepository,
                        OrderValidator orderValidator) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
    }

    @Transactional
    public void placeOrder(Cart cart) {
        Order order = orderMapper.mapFrom(cart);
        order.place(orderValidator);
        orderRepository.save(order);
    }

    @Transactional
    public void payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        order.payed();
        orderRepository.save(order);       // Domain Event 발행을 위해
    }

    @Transactional
    public void deliverOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        order.delivered();
        orderRepository.save(order);       // Domain Event 발행을 위해
    }
}
