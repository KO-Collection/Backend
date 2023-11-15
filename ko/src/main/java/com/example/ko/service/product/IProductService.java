package com.example.ko.service.product;

import com.example.ko.dto.home.IProductSearchHome;
import com.example.ko.model.Product;
import org.springframework.data.repository.query.Param;

public interface IProductService {
    IProductSearchHome getProductByIdProductForCart(Long idProduct);
    Product getProductById(Long idProduct);

}
