package com.microshop.stockmanagement.cartservice.service;

import com.microshop.stockmanagement.cartservice.entity.Order;
import com.microshop.stockmanagement.cartservice.entity.User;

public interface OrderService {

    Order saveOrder(Order order, User user);
}
