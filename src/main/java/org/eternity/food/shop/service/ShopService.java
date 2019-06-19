package org.eternity.food.shop.service;

import org.eternity.food.shop.domain.Menu;
import org.eternity.food.shop.domain.MenuRepository;
import org.eternity.food.shop.domain.Shop;
import org.eternity.food.shop.domain.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShopService {
    private ShopRepository shopRepository;
    private MenuRepository menuRepository;

    public ShopService(ShopRepository shopRepository,
                       MenuRepository menuRepository) {
        this.shopRepository = shopRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional(readOnly = true)
    public MenuBoard getMenuBoard(Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(IllegalArgumentException::new);
        List<Menu> menus = menuRepository.findByShopId(shopId);
        return new MenuBoard(shop, menus);
    }
}
