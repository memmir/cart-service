package com.microshop.stockmanagement.cartservice.utilities;

import com.microshop.stockmanagement.cartservice.entity.Product;
import com.microshop.stockmanagement.cartservice.response.ProductResponse;

import java.math.BigDecimal;

public class CartUtilities {

    public static BigDecimal getSubTotalForItem(Product product, int quantity){
        return (product.getPrice()).multiply(BigDecimal.valueOf(quantity));
    }
}
