package com.microshop.stockmanagement.cartservice.service;


import com.microshop.stockmanagement.cartservice.entity.Order;
import com.microshop.stockmanagement.cartservice.entity.User;
import com.microshop.stockmanagement.cartservice.repository.OrderRepository;
import com.microshop.stockmanagement.cartservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    @Transactional
    @Override
    public Order saveOrder(Order order, User user) {

        userRepository.save(user);

        return orderRepository.save(order);

    }
}
