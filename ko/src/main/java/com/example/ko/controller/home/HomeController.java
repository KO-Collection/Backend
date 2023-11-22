package com.example.ko.controller.home;

import com.example.ko.dto.home.IProductSearchHome;
import com.example.ko.dto.home.ISizeProduct;
import com.example.ko.model.Product;
import com.example.ko.model.Size;
import com.example.ko.service.home.IHomeService;
import com.example.ko.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
    @Autowired
    private IHomeService homeService;
    @Autowired
    IProductService productService;

    @GetMapping("/bestsellers")
    public ResponseEntity<List<IProductSearchHome>> getBestsellers() {
        List<IProductSearchHome> getBestsellers = homeService.getBestSeller();
        if (getBestsellers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(getBestsellers, HttpStatus.OK);
    }

    @GetMapping("/new")
    public ResponseEntity<List<IProductSearchHome>> getListNew() {
        List<IProductSearchHome> getBestsellers = homeService.getProductNew();
        if (getBestsellers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(getBestsellers, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<IProductSearchHome>> getListByName(
            @RequestParam(defaultValue = "0", required = false, name = "_page") Integer page,
            @RequestParam(value = "name", defaultValue = "", required = false) String name) {
        Pageable pageable1 = PageRequest.of(page, 8, Sort.by("idProduct").ascending());
        Page<IProductSearchHome> getListByName = homeService.findProductSearchHome(pageable1, name);
        if (getListByName.getTotalElements() != 0) {
            return ResponseEntity.ok(getListByName);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/size")
    public ResponseEntity<List<ISizeProduct>> getSizeProduct(
            @RequestParam(defaultValue = "0", name = "id_product") Long idProduct
    ) {
        Product product1 = productService.getProductById(idProduct);
        if (product1 != null) {
            List<ISizeProduct> sizeList = productService.getSizeProduct(product1.getProductId());
            return new ResponseEntity<>(sizeList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
