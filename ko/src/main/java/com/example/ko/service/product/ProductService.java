package com.example.ko.service.product;

import com.example.ko.dto.home.IProductSearchHome;
import com.example.ko.dto.home.ISearch;
import com.example.ko.dto.home.ISizeProduct;
import com.example.ko.model.ColorProduct;
import com.example.ko.model.Product;
import com.example.ko.model.Size;
import com.example.ko.model.TypeProduct;
import com.example.ko.repository.IProductRepository;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

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

    @Override
    public List<ISizeProduct> getSizeProduct(Long idProduct) {
        return productRepository.getSizeProduct(idProduct);
    }

    @Override
    public List<IProductSearchHome> findAllProductSale(String time, String[] colorList, Double minPrice, Double maxPrice, String[] typeList) {
        if (time.equals("")){
            time = "%%";
        }

        if (colorList.length == 0 && typeList.length == 0 ){
            String[] colorListNew = new String[]{"Vàng","Đỏ","Hồng","Tím","Đen","Trắng","Xanh da trời","Đà","Nâu"};
            String[] typeListNew = new String[]{"Áo sơ mi tay ngắn","Áo sơ mi tay dài","Áo sơ mi tay lỡ","Quần dài","Quần short","Quần lửng","Áo vest","Áo khoác kiểu","Măng tô"};
            return productRepository.findAllProduct(time,colorListNew,minPrice,maxPrice,typeListNew);
        }

        if (colorList.length == 0){
            String[] colorListNew = new String[]{"Vàng","Đỏ","Hồng","Tím","Đen","Trắng","Xanh da trời","Đà","Nâu"};
            return productRepository.findAllProduct(time,colorListNew,minPrice,maxPrice,typeList);
        }
        if (typeList.length == 0){
            String[] typeListNew = new String[]{"Áo sơ mi tay ngắn","Áo sơ mi tay dài","Áo sơ mi tay lỡ","Quần dài","Quần short","Quần lửng","Áo vest","Áo khoác kiểu","Măng tô"};
            return productRepository.findAllProduct(time,colorList,minPrice,maxPrice,typeListNew);
        }

        return productRepository.findAllProduct(time,colorList,minPrice,maxPrice,typeList);
    }

    @Override
    public Page<IProductSearchHome> getAllProduct(Pageable pageable, String name, List<Long> size, List<Long> color, List<Long> typeProducts) {
        return productRepository.findAllProductHomePrice(pageable,name);
    }

    @Override
    public List<ISearch> getAllSize() {
        return productRepository.getSizeSearch();
    }

    @Override
    public List<ISearch> getAllColor() {
        return productRepository.getColorProduct();
    }


    @Override
    public List<ISearch> getAllType() {
        return productRepository.getTypeSearch();
    }
}
