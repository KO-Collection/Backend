package com.example.ko.service.order;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IOrderService {
    void deleteOrder(Long idUser,Long idOrderBill);
    Long createOrderBill(Long idUser);
    void createOrderDetail(Long idOrderBill,Long idProduct,Long quantity,Double price);
}
