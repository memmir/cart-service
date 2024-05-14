package com.microshop.stockmanagement.cartservice.redis;

import java.util.Collection;

public interface CartRedisRepository {

    void addItemToCart(String key, Object tem);

    Collection<Object> getCart(String key, Class type);

    void deleteItemFromCart(String key, Object item);

    void deleteCart(String key);
}
