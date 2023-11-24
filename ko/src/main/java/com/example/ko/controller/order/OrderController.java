package com.example.ko.controller.order;

import com.example.ko.dto.home.IHistoryOrder;
import com.example.ko.dto.home.IOrderDetail;
import com.example.ko.dto.home.IProductSearchHome;
import com.example.ko.model.Product;
import com.example.ko.model.Users;
import com.example.ko.service.cart.ICartService;
import com.example.ko.service.order.IOrderService;
import com.example.ko.service.product.IProductService;
import com.example.ko.service.user.IUserService;
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
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    IUserService userService;
    @Autowired
    ICartService cartService;
    @Autowired
    IProductService productService;
    @Autowired
    IOrderService orderService;
    @PostMapping("/create")
    public ResponseEntity<Object> createOrderBill(@RequestParam(defaultValue = "1", name="name_user") String nameUser){
        Users users = userService.findByUserName(nameUser);
        Long idUSer = users.getUserId();
        if (idUSer == null){
            return new ResponseEntity<>("Không tìm thấy idUser", HttpStatus.NOT_ACCEPTABLE);
        }
         Long idOrder = orderService.createOrderBill(idUSer);
        return new ResponseEntity<>(idOrder, HttpStatus.OK);
    }
    @PostMapping("/order-bill")
    public ResponseEntity<String> createOrderBillDetail(@RequestParam("id_product") Long idProduct,
                                                        @RequestParam("quantity") Long quantity,
                                                        @RequestParam("price") Double price,
                                                        @RequestParam("id_order_bill") Long idOrderBill,
                                                        @RequestParam("id_size") Long idSize
    ) {

        Product product1 = productService.getProductById(idProduct);
        if (product1 == null) {
            return new ResponseEntity<>("Không tìm thấy sản phẩm", HttpStatus.NOT_ACCEPTABLE);
        }
        orderService.createOrderDetail(idOrderBill, idProduct, quantity, price,idSize);
        return new ResponseEntity<>("Thanh toán thành công!", HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<Object> getHistoryOrder(
            @RequestParam(defaultValue = "0", required = false, name = "_page") Integer page,
            @RequestParam(name = "name_user") String nameUser
    ) {
        Pageable pageable1 = PageRequest.of(page,5, Sort.by("dayOrder").descending());
        Page<IHistoryOrder> getListOrder = orderService.getOrderBillByIdUser(pageable1, nameUser);
        if (!(getListOrder.isEmpty())) {
            return ResponseEntity.ok(getListOrder);
        }
        return ResponseEntity.noContent().build();

    }
    @GetMapping("/detail")
    public ResponseEntity<?> getDetailOrder(
            @RequestParam(defaultValue = "0", required = false, name = "page") Integer page,
            @RequestParam(name = "name_user") String nameUser,
            @RequestParam(name = "time") String time
    ) {
        Pageable pageable1 = PageRequest.of(page,5, Sort.by("timeOrder").descending());
        Page<IOrderDetail> getListOrder = orderService.getOrderBillDetail(pageable1, nameUser,time);
        if (!getListOrder.isEmpty()) {
            return new ResponseEntity<>(getListOrder, HttpStatus.OK);
        }
        return ResponseEntity.noContent().build();
    }
}
