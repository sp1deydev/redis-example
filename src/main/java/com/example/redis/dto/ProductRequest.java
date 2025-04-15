package com.example.redis.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
    private String productName;
    private Integer quantity;
}
