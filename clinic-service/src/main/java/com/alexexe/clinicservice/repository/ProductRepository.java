package com.alexexe.clinicservice.repository;

import com.alexexe.clinicservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
