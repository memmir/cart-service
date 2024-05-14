package com.microshop.stockmanagement.cartservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class ProductResponse {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private Long productCreatedDate;
    private Long productUpdatedDate;


}
