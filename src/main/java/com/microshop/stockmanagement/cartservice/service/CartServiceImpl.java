package com.microshop.stockmanagement.cartservice.service;

import com.microshop.stockmanagement.cartservice.entity.Item;
import com.microshop.stockmanagement.cartservice.entity.Product;
import com.microshop.stockmanagement.cartservice.enums.Language;
import com.microshop.stockmanagement.cartservice.feign.product.ProductServiceFeignClient;
import com.microshop.stockmanagement.cartservice.redis.CartRedisRepository;
import com.microshop.stockmanagement.cartservice.response.ProductResponse;
import com.microshop.stockmanagement.cartservice.utilities.CartUtilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {


    private final ProductServiceFeignClient productServiceFeignClient;

    private final CartRedisRepository cartRedisRepository;


    @Override
    public void addItemToCart(String cartId, Long productId, Integer quantity, Language language) {

        ProductResponse productResponse = productServiceFeignClient.getProduct(language, productId).getPayload();

        Product product = new Product();

        product.setProductId(productResponse.getProductId());
        product.setProductName(productResponse.getProductName());
        product.setPrice(productResponse.getPrice());
        product.setQuantity(quantity);

        Item item = new Item(quantity, product, CartUtilities.getSubTotalForItem(product,quantity));

        cartRedisRepository.addItemToCart(cartId, item);

    }

    @Override
    public List<Object> getCart(String cartId) {

        return (List<Object>) cartRedisRepository.getCart(cartId, Item.class);
    }

    @Override
    public void changeItemQuantity(String cartId, Long productId, Integer quantity) {

        List<Item> cart = (List)cartRedisRepository.getCart(cartId, Item.class);
        for (Item item: cart){
            if((item.getProduct().getProductId()).equals(productId)){
                cartRedisRepository.deleteItemFromCart(cartId, item);
                item.setQuantity(quantity);
                item.setSubTotal(CartUtilities.getSubTotalForItem(item.getProduct(),quantity));
                cartRedisRepository.addItemToCart(cartId, item);
            }
        }
    }

    @Override
    public void deleteItemFromCart(String cartId, Long productId) {
        List<Item> cart = (List) cartRedisRepository.getCart(cartId, Item.class);
        for(Item item : cart){
            if(item.getProduct().getProductId().equals(productId)){
                cartRedisRepository.deleteItemFromCart(cartId, item);
            }
        }
    }

    @Override
    public boolean checkIfItemExists(String cartId, Long productId) {
        List<Item> cart = (List)cartRedisRepository.getCart(cartId, Item.class);
        for(Item item: cart){
            if((item.getProduct().getProductId().equals(productId))){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Item> getAllItemsFromCart(String cartId) {
        List<Item> items = (List)cartRedisRepository.getCart(cartId, Item.class);
        return items;
    }

    @Override
    public void deleteCart(String cartId) {
        cartRedisRepository.deleteCart(cartId);
    }
}
