package com.example.redis.redis.entity;

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
public class ProductRedis implements Serializable {
    @Id
    Long id;
    String productName;
    Integer quantity;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private long timeToLive;
}
