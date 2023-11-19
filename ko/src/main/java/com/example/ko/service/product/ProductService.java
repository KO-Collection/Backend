package com.example.ko.service.product;

import com.example.ko.dto.home.IProductSearchHome;
import com.example.ko.model.Product;
import com.example.ko.repository.IProductRepository;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {
    @Autowired
    IProductRepository productRepository;
    @Override
    public IProductSearchHome getProductByIdProductForCart(Long idProduct) {
        return productRepository.getProductByIdProductForCart(idProduct);
    }

    @Override
    public Product getProductById(Long idProduct) {
        return productRepository.findById(idProduct).orElse(null);
    }

}
