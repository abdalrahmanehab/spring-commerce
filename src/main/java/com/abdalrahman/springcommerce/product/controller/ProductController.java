package com.abdalrahman.springcommerce.product.controller;

import com.abdalrahman.springcommerce.product.dtos.ProductRequest;
import com.abdalrahman.springcommerce.product.dtos.ProductResponse;
import com.abdalrahman.springcommerce.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

}
