package com.abdalrahman.springcommerce.product.service;

import com.abdalrahman.springcommerce.product.Product;
import com.abdalrahman.springcommerce.product.dtos.ProductRequest;
import com.abdalrahman.springcommerce.product.dtos.ProductResponse;
import com.abdalrahman.springcommerce.product.repository.ProductRepository;
import com.abdalrahman.springcommerce.shared.errors.exceptions.DuplicateSkuException;
import com.abdalrahman.springcommerce.shared.utils.enums.SortDirection;
import com.abdalrahman.springcommerce.shared.utils.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) throws DuplicateSkuException {

        final Optional<Product> optionalProduct = productRepository.findBySku(productRequest.getSku());

        if(optionalProduct.isPresent()){
            throw new DuplicateSkuException ("Product Already found!");
        }

        Product product = ProductMapper.toProduct(productRequest);

        return ProductMapper.toProductResponse(productRepository.save(product));

    }

    @Override
    public ProductResponse getProductById(Long id) {
        return null;
    }

    @Override
    public ProductResponse getProductBySku(String sku) {
        return null;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return List.of();
    }

    @Override
    public List<ProductResponse> getProductsByBrand(String brand) {
        return List.of();
    }

    @Override
    public List<ProductResponse> getProductsByPriceRange(BigDecimal min, BigDecimal max) {
        return List.of();
    }

    @Override
    public List<ProductResponse> getAllProductsSortedByPrice(SortDirection direction) {
        return List.of();
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
