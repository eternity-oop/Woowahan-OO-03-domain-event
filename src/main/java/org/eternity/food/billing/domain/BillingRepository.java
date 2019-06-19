package org.eternity.food.billing.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillingRepository extends JpaRepository<Billing, Long> {
    Optional<Billing> findByShopId(Long shopId);
}
