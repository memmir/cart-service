package com.microshop.stockmanagement.cartservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microshop.stockmanagement.cartservice.response.ProductResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item", schema = "stock_management")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "subtotal")
    private BigDecimal subTotal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToMany( mappedBy = "items")
    @JsonIgnore
    private List<Order> orders;


    public Item(Integer quantity, Product product, BigDecimal subTotalForItem) {
        this.quantity = quantity;
        this.product = product;
        this.subTotal = subTotalForItem;
    }


}
