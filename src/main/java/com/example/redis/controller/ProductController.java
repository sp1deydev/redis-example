package com.example.redis.controller;

import com.example.redis.dto.ProductRequest;
import com.example.redis.dto.ProductResponse;
import com.example.redis.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;

    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable String id) {
        return productService.getProduct(Long.parseLong(id));
    }
}
