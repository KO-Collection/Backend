package com.example.ko.service.product;

import com.example.ko.dto.home.IProductSearchHome;
import com.example.ko.dto.home.ISearch;
import com.example.ko.dto.home.ISizeProduct;
import com.example.ko.model.Product;
import com.example.ko.model.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductService {
    IProductSearchHome getProductByIdProductForCart(Long idProduct);
    Product getProductById(Long idProduct);
    List<ISizeProduct> getSizeProduct(Long idProduct);
    List<ISearch> getAllSize();
    List<ISearch> getAllColor();
    List<ISearch> getAllType();

    List<IProductSearchHome> findAllProductSale(String time,String[] colorList,Double minPrice,Double maxPrice,String[] typeList);
    Page<IProductSearchHome> getAllProduct(Pageable pageable, String name, List<Long> size, List<Long> color, List<Long> typeProducts);

}
