package com.example.redis.dto;

import com.example.redis.entity.Product;
import com.example.redis.redis.entity.ProductRedis;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String productName;
    private Integer quantity;

    public ProductResponse(Product product) {
        this(product.getId(), product.getProductName(), product.getQuantity());
    }

    public ProductResponse(Long id, String productName, Integer quantity) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
    }

    public ProductResponse(ProductRedis productRedis) {
        this(productRedis.getId(), productRedis.getProductName(), productRedis.getQuantity());
    }

//    public ProductResponse(Product product) {
//        this.id = product.getId();
//        this.productName = product.getProductName();
//        this.quantity = product.getQuantity();
//    }
}
