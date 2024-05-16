package com.microshop.stockmanagement.cartservice.controller;


import com.microshop.stockmanagement.cartservice.enums.Language;
import com.microshop.stockmanagement.cartservice.response.InternalApiResponse;
import com.microshop.stockmanagement.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/1.0/cart")
@Slf4j
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/get/{cartId}")
    public InternalApiResponse<List<Object>> getCart(@PathVariable("cartId") String cartId){
        List<Object> cart = cartService.getCart(cartId);
        if(!cart.isEmpty()) {
            return InternalApiResponse.<List<Object>>builder()
                    .httpStatus(HttpStatus.OK)
                    .hasError(false)
                    .payload(cart)
                    .build();
        }
        return InternalApiResponse.<List<Object>>builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/addtocart/{language}/{cartId}", params = {"productId", "quantity"})
    public InternalApiResponse<List<Object>> addItemToCart(@RequestParam("productId") Long productId,
                                                           @RequestParam("quantity") Integer quantity,
                                                           @PathVariable("language") Language language,
                                                           @PathVariable("cartId") String cartId){

        List<Object> cart = cartService.getCart(cartId);
        if(cart != null){
            if(cart.isEmpty()){
                cartService.addItemToCart(cartId,productId,quantity,language);
            }else {
                if(cartService.checkIfItemExists(cartId,productId)){
                    cartService.changeItemQuantity(cartId, productId, quantity);
                }else {
                    cartService.addItemToCart(cartId,productId,quantity,language);
                }
            }
            return InternalApiResponse.<List<Object>>builder()
                    .httpStatus(HttpStatus.OK)
                    .hasError(false)
                    .payload(cart)
                    .build();

        }
        return InternalApiResponse.<List<Object>>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/delete/{cartId}", params = {"productId"})
    public InternalApiResponse<Void> removeItemFromCart(@PathVariable("cartId") String cartId,
                                                        @RequestParam("productId") Long productId){
        List<Object> cart = cartService.getCart(cartId);
        if(cart != null) {
            cartService.deleteItemFromCart(cartId, productId);
            return InternalApiResponse.<Void>builder()
                    .httpStatus(HttpStatus.OK)
                    .hasError(false)
                    .build();
        }
        return InternalApiResponse.<Void>builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .build();
    }



}
