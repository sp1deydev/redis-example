package com.example.redis.redis.entity;

import com.example.redis.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RedisHash("product")
@NoArgsConstructor
public class ProductRedis implements Serializable {
    @Id
    Long id;
    String productName;
    Integer quantity;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private long timeToLive;

    public ProductRedis(Product product, long timeToLive) {
        this(product.getId(), product.getProductName(), product.getQuantity());
        this.timeToLive = timeToLive;
    }

    public ProductRedis(Long id, String productName, Integer quantity) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
    }
}
