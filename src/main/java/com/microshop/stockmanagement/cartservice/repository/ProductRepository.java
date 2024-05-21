package com.microshop.stockmanagement.cartservice.repository;

import com.microshop.stockmanagement.cartservice.entity.Order;
import com.microshop.stockmanagement.cartservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {


}
