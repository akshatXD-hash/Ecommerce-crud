package com.akshat.Ecommerce.services;

import com.akshat.Ecommerce.model.Product;
import com.akshat.Ecommerce.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public List<Product> getProducts() {
        return repo.findAll();
    }

    public Product getById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return repo.save(product);
    }

    public Product updateProduct(int productId, Product product, MultipartFile image) throws IOException {
        Product existingProduct = repo.findById(productId).orElse(null);

        if (existingProduct == null) {
            return null;
        }

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setReleaseDate(product.getReleaseDate());
        existingProduct.setAvailable(product.isAvailable());
        existingProduct.setStockQuantity(product.getStockQuantity());

        existingProduct.setImageName(image.getOriginalFilename());
        existingProduct.setImageType(image.getContentType());
        existingProduct.setImageData(image.getBytes());

        return repo.save(existingProduct);
    }

    public void deleteById(int productId) {
        repo.deleteById(productId);
    }

    public List<Product> findBykeyword(String keyword) {
        return repo.findByKeyword(keyword);
    }
}
