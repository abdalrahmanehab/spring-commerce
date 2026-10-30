package com.abdalrahman.springcommerce.product.service;

import com.abdalrahman.springcommerce.product.Product;
import com.abdalrahman.springcommerce.product.dtos.ProductRequest;
import com.abdalrahman.springcommerce.product.dtos.ProductResponse;
import com.abdalrahman.springcommerce.product.repository.ProductRepository;
import com.abdalrahman.springcommerce.shared.errors.exceptions.DuplicateSkuException;
import com.abdalrahman.springcommerce.shared.errors.exceptions.ResourceNotFoundException;
import com.abdalrahman.springcommerce.shared.utils.enums.SortDirection;
import com.abdalrahman.springcommerce.shared.utils.mapper.ProductMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse create(final ProductRequest productRequest) throws DuplicateSkuException {

        productRepository.findBySku(productRequest.getSku())
                .ifPresent(p -> {
                    log.warn("Duplicate SKU detected [sku={}]", productRequest.getSku());
                    throw new DuplicateSkuException("Product Already found!");
                });

        Product product = ProductMapper.toProduct(productRequest);
        ProductResponse response = ProductMapper.toProductResponse(productRepository.save(product));

        log.info("Product created [id={}, sku={}]", response.id(), response.sku());
        return response;
    }

    @Override
    public ProductResponse getById(final Long id) throws ResourceNotFoundException {
        return productRepository.findById(id)
                .map(ProductMapper::toProductResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }

    @Override
    public ProductResponse getBySku(final String sku) throws ResourceNotFoundException {
        return productRepository.findBySku(sku)
                .map(ProductMapper::toProductResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with SKU: " + sku));
    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toProductResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getAllByBrand(final String brand) {
        return productRepository.findAllByBrand(brand).stream()
                .map(ProductMapper::toProductResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getAllInPriceRange(final BigDecimal min, final BigDecimal max) {
        return productRepository.findAllInPriceRange(min, max).stream()
                .map(ProductMapper::toProductResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getAllSortedByPrice(final SortDirection direction) {
        return productRepository.findAllSortedByPrice(direction).stream()
                .map(ProductMapper::toProductResponse)
                .toList();
    }

    @Override
    @Transactional
    public ProductResponse update(
            final Long id,
            final ProductRequest request)
            throws ResourceNotFoundException, DuplicateSkuException {

        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        productRepository.findBySku(request.getSku())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(p -> {
                    throw new DuplicateSkuException("SKU already taken");
                });

        productToUpdate.setName(request.getName());
        productToUpdate.setDescription(request.getDescription());
        productToUpdate.setPrice(request.getPrice());
        productToUpdate.setQuantity(request.getQuantity());
        productToUpdate.setBrand(request.getBrand());
        productToUpdate.setSku(request.getSku());

        Product updatedProduct = productRepository.update(productToUpdate);

        ProductResponse response = ProductMapper.toProductResponse(updatedProduct);

        log.info("Product updated [id={}, sku={}]", response.id(), response.sku());
        return response;
    }

    @Override
    public void delete(final Long id) throws ResourceNotFoundException {
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        productRepository.delete(id);
        log.info("Product deleted [id={}]", id);
    }

    @Override
    public void deleteAll() {
        log.warn("Deleting ALL products");
        productRepository.deleteAll();
    }
}
