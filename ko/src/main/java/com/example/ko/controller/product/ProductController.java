package com.example.ko.controller.product;

import com.example.ko.model.Img;
import com.example.ko.model.Product;
import com.example.ko.service.img.IImgService;
import com.example.ko.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    IProductService productService;
    @Autowired
    IImgService imgService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product.getProductId() != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/img/{id}")
    public ResponseEntity<Object> findImgProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);

        if (product.getProductId() != null) {
            List<String> imgList = imgService.getAllImg(id);
            if (product == null) {
                return new ResponseEntity<>("Không tìm thấy giỏ hàng", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(imgList, HttpStatus.OK);
        }
        return new ResponseEntity<>("Không tìm thấy sản phẩm", HttpStatus.NOT_FOUND);
    }
}
