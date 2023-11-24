package com.example.ko.controller.product;

import com.example.ko.dto.home.IProductSearchHome;
import com.example.ko.dto.home.ISearch;
import com.example.ko.model.Product;
import com.example.ko.service.img.IImgService;
import com.example.ko.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    IProductService productService;
    @Autowired
    IImgService imgService;
    @GetMapping("/list")
    public ResponseEntity<Object> getAllPRoduct(
            @RequestParam(name = "time",defaultValue = "") String time,
            @RequestParam(name = "color",defaultValue = "") String[] colorList,
            @RequestParam(defaultValue = "0", name = "min") double minPrice,
            @RequestParam(defaultValue = "12000000", name = "max") double maxPrice,
            @RequestParam(name = "type",defaultValue = "") String[] typeList

    ) {

        List<IProductSearchHome> getProductList = productService.findAllProductSale(time,colorList,minPrice,maxPrice,typeList);
        if (getProductList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(getProductList, HttpStatus.OK);

    }
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

    @GetMapping("/size")
    public ResponseEntity<Object> getALlSize() {
        List<ISearch> sizeList = productService.getAllSize();
        return new ResponseEntity<>(sizeList,HttpStatus.OK);
    }
    @GetMapping("/color")
    public ResponseEntity<Object> getAllColor() {
        List<ISearch> colorList = productService.getAllColor();
        return new ResponseEntity<>(colorList,HttpStatus.OK);
    }
    @GetMapping("/type")
    public ResponseEntity<Object> getAllType() {
        List<ISearch> typeList = productService.getAllType();
        return new ResponseEntity<>(typeList,HttpStatus.OK);
    }
}
