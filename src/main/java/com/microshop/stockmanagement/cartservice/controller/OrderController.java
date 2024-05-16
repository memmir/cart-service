package com.microshop.stockmanagement.cartservice.controller;

import com.microshop.stockmanagement.cartservice.entity.Item;
import com.microshop.stockmanagement.cartservice.entity.Order;
import com.microshop.stockmanagement.cartservice.entity.User;
import com.microshop.stockmanagement.cartservice.enums.Language;
import com.microshop.stockmanagement.cartservice.feign.user.UserServiceFeignClient;
import com.microshop.stockmanagement.cartservice.response.InternalApiResponse;
import com.microshop.stockmanagement.cartservice.response.UserResponse;
import com.microshop.stockmanagement.cartservice.service.CartService;
import com.microshop.stockmanagement.cartservice.service.OrderService;
import com.microshop.stockmanagement.cartservice.utilities.OrderUtilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value="/api/1.0/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private UserServiceFeignClient userServiceFeignClient;

    private OrderService orderService;

    private CartService cartService;

    @PostMapping(value = "/order/{language}/{userId}/{cartId}")
    public InternalApiResponse<Order> saveOrder(@PathVariable("userId") Long userId,
                                                @PathVariable("cartId") String cartId,
                                                @PathVariable("language")Language language){

        List<Item> cart = cartService.getAllItemsFromCart(cartId);
        UserResponse userResponse = userServiceFeignClient.getUser(language, userId).getPayload();
        User user = new User();

        user.setUserId(userResponse.getUserId());
        user.setUserName(user.getUserName());
        user.setUserSurname(user.getUserSurname());
        user.setUserPhoneNumber(user.getUserPhoneNumber());
        user.setUserEmail(user.getUserEmail());
        user.setUserPassword(user.getUserPassword());
        user.setUserAddress(user.getUserAddress());


        if(cart != null && user != null){
            Order order = this.createOrder(cart,user);
            try{

                orderService.saveOrder(order);
                cartService.deleteCart(cartId);

                return InternalApiResponse.<Order>builder()
                        .httpStatus(HttpStatus.OK)
                        .hasError(false)
                        .payload(order)
                        .build();

            }catch (Exception e){
                e.printStackTrace();

                return InternalApiResponse.<Order>builder()
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .hasError(true)
                        .build();
            }
        }

        return InternalApiResponse.<Order>builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .build();
    }

    private Order createOrder(List<Item> cart, User user){
        Order order = new Order();
        order.setItems(cart);
        order.setUser(user);
        order.setTotal(OrderUtilities.countTotalPrice(cart));
        order.setOrderedDate(LocalDate.now());
        order.setStatus("PAYMENT_EXPECTED");
        return order;
    }
}
