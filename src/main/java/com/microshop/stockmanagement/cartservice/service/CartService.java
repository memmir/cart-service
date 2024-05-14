package com.microshop.stockmanagement.cartservice.service;

import com.microshop.stockmanagement.cartservice.entity.Item;
import com.microshop.stockmanagement.cartservice.enums.Language;

import java.util.List;

public interface CartService {

    void addItemToCart(String cartId, Long productId, Integer quantity, Language language);

    List<Object> getCart(String cartId);

    void changeItemQuantity(String cartId, Long productId, Integer quantity);

    void deleteItemFromCart(String cartId, Long productId);

    boolean  checkIfItemExists(String cartId, Long productId);

    List<Item> getAllItemsFromCart(String cartId);

    void deleteCart(String cartId);

}
