package com.softuni.productshop.repository;

import com.softuni.productshop.domain.dtos.ProductInRangeDto;
import com.softuni.productshop.domain.entitie.Product;
import com.softuni.productshop.domain.entitie.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository <Product, Integer >{

    List<Product> findAllByPriceBetweenAndBuyerIdOrderByPrice(BigDecimal more, BigDecimal less, User buyer);
}
