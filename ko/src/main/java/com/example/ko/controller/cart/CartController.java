package com.example.ko.controller.cart;

import com.example.ko.dto.home.IProductSearchHome;
import com.example.ko.model.Product;
import com.example.ko.model.Users;
import com.example.ko.service.cart.ICartService;
import com.example.ko.service.product.IProductService;
import com.example.ko.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    @Autowired
    ICartService cartService;
    @Autowired
    IProductService productService;
    @Autowired
    IUserService userService;
    @PostMapping("/create")
    public ResponseEntity<String> createCart(@RequestParam(defaultValue = "1", name="name_user") String nameUser,
                                             @RequestParam (defaultValue = "0", name = "id_product") Long idProduct,
                                             @RequestParam (defaultValue = "0",name = "quantity") Long quantity) {
        Users users = userService.findByUserName(nameUser);
        Long idUSer = users.getUserId();
        Product product1 = productService.getProductById(idProduct);
        if (idProduct == 0 || idProduct < 1) {
            return new ResponseEntity<>("Không tìm thấy idProduct", HttpStatus.NOT_ACCEPTABLE);
        }
        if (idUSer == null || idUSer < 1) {
            return new ResponseEntity<>("Không tìm thấy idUser", HttpStatus.NOT_ACCEPTABLE);
        }
        if (quantity == null) {
            return new ResponseEntity<>("Sai số lượng chọn hàng", HttpStatus.NOT_ACCEPTABLE);
        }
        Long checkQuantityProduct = cartService.quantityProduct(idProduct);
        Long checkQuantityCart = cartService.quantityProductCart(idProduct, idUSer);

        if (checkQuantityProduct < 1) {
            return new ResponseEntity<>("Chọn không thành công sản phẩm  đã hết hàng", HttpStatus.CREATED);
        }
        if (checkQuantityCart != null) {
            if ((checkQuantityCart + quantity) > checkQuantityProduct) {
                return new ResponseEntity<>("Chọn không thành công số lượng  vượt quá số lượng kho", HttpStatus.CREATED);
            } else if ((checkQuantityCart + quantity) < 0) {
                return new ResponseEntity<>("Chọn không thành công số lượng  đã hết", HttpStatus.CREATED);
            }
        }
        cartService.createCard(idUSer, idProduct, quantity);
        return new ResponseEntity<>(product1.getProductName() + "đã được thêm vào giỏ hàng", HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity<Object> getAllCart(@RequestParam(defaultValue = "1", name="user_name") String nameUser){
        Users users = userService.findByUserName(nameUser);
        if (users.getUserId() != null) {
            List<IProductSearchHome> cart = cartService.getAllCart(users.getUserId());
            if (cart == null) {
                return new ResponseEntity<>("Không tìm thấy giỏ hàng", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        return new ResponseEntity<>("Không tìm thấy tài khoản", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteCart(@RequestParam(defaultValue = "1", name = "user_name") String nameUser,
                                             @RequestParam(defaultValue = "0", name = "id_product") Long idProduct
    ) {
        Users users = userService.findByUserName(nameUser);
        Long idUsers = users.getUserId();
        Product product1 = productService.getProductById(idProduct);
        if (users == null) {
            return new ResponseEntity<>("Không tìm thấy tài khoản", HttpStatus.NOT_FOUND);
        }
        if (product1 == null) {
            return new ResponseEntity<>("Không có sản phẩm trong giỏ hàng", HttpStatus.NOT_FOUND);
        }
      cartService.deleteCart(idUsers,idProduct);
        return new ResponseEntity<>(product1.getProductName() + "đã xóa khỏi giỏ hàng", HttpStatus.OK);
    }

}
