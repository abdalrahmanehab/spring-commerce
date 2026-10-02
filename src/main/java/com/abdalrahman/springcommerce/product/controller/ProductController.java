package com.abdalrahman.springcommerce.product.controller;

import com.abdalrahman.springcommerce.product.dtos.ProductRequest;
import com.abdalrahman.springcommerce.product.dtos.ProductResponse;
import com.abdalrahman.springcommerce.product.service.ProductService;
import com.abdalrahman.springcommerce.shared.utils.enums.SortDirection;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductRequest productRequest) {
        return productService.create(productRequest);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
        return productService.update(id, productRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/id/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping("/sku/{sku}")
    public ProductResponse findBySku(@PathVariable String sku) {
        return productService.getBySku(sku);
    }

    @GetMapping()
    public List<ProductResponse> findAll() {
        return productService.getAll();
    }

    @GetMapping("/brand/{brand}")
    public List<ProductResponse> findAllByBrand(@PathVariable String brand) {
        return productService.getAllByBrand(brand);
    }

    @GetMapping("/price-range")
    public List<ProductResponse> findAllInPriceRange(
            @RequestParam @NotNull BigDecimal min,
            @RequestParam @NotNull BigDecimal max) {
        return productService.getAllInPriceRange(min, max);
    }

    @GetMapping("/sorted")
    public List<ProductResponse> findAllSortedByPrice(@RequestParam SortDirection direction) {
        return productService.getAllSortedByPrice(direction);
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        productService.deleteAll();
    }


}
