package com.example.redis.redis.repository;

import com.example.redis.redis.entity.ProductRedis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRedisRepository extends JpaRepository<ProductRedis, Long> {
}
