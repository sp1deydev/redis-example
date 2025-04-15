package com.example.redis.service;

import com.example.redis.dto.ProductRequest;
import com.example.redis.dto.ProductResponse;
import com.example.redis.entity.Product;
import com.example.redis.redis.entity.ProductRedis;
import com.example.redis.redis.repository.ProductRedisRepository;
import com.example.redis.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductRedisRepository productRedisRepository;

    public ProductResponse createProduct(ProductRequest request) {
        this.validateCreateProductRequest(request);

        log.info("[createProduct] - create product with name: {} START", request.getProductName());

        Product newProduct = new Product();
        newProduct.setProductName(request.getProductName());
        newProduct.setQuantity(request.getQuantity());

        Product response = productRepository.save(newProduct);

        log.info("[createProduct] - create product with name: {} END", request.getProductName());
        return ProductResponse.builder()
                .id(response.getId())
                .productName(response.getProductName())
                .quantity(response.getQuantity())
                .build();
    }
    // get products not using redis cache
    public List<Product> getProducts() {
        log.info("[getProducts] - get list products START");

        List<Product> products =  productRepository.findAll();

        log.info("[getProducts] - get list products END");
        return products;
    }

    // get product detail using redis cache
    public ProductResponse getProduct(Long id) {
        log.info("[getProduct] - get product detail with id: {} START", id);

        Optional<ProductRedis> cacheOpt = productRedisRepository.findById(id);

        if(cacheOpt.isPresent()) {
            log.info("[getProduct] - get product detail with id: {} in cache END", id);
            return ProductResponse.builder()
                    .id(cacheOpt.get().getId())
                    .productName(cacheOpt.get().getProductName())
                    .quantity(cacheOpt.get().getQuantity())
                    .build();
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product name not found"));

        ProductRedis newProductRedis = new ProductRedis();
        newProductRedis.setId(product.getId());
        newProductRedis.setProductName(product.getProductName());
        newProductRedis.setQuantity(product.getQuantity());
        newProductRedis.setTimeToLive(1000);

        productRedisRepository.save(newProductRedis);
        ProductResponse response = new ProductResponse(product);

        log.info("[getProduct] - get product detail with id: {} END", id);
        return response;
    }



    private void validateCreateProductRequest(ProductRequest request) {
        if(StringUtils.isBlank(request.getProductName())) {
            throw new RuntimeException("Product name is invalid");
        }
        if(request.getQuantity() == null) {
            throw new RuntimeException("Product quantity is invalid");
        }
    }
}
