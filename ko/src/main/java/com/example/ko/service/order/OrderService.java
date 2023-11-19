package com.example.ko.service.order;

import com.example.ko.repository.IOrderBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class OrderService implements IOrderService{
    @Autowired
    IOrderBillRepository orderBillRepository;
    @Override
    public void deleteOrder(Long idUser, Long idOrderBill) {

    }

    @Override
    public Long createOrderBill(Long idUser) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
         orderBillRepository.createOrderBill(idUser,localDate,localDateTime);
         return orderBillRepository.getIdBestNew();
    }

    @Override
    public void createOrderDetail(Long idOrderBill, Long idProduct, Long quantity, Double price) {
        orderBillRepository.createOrderBillDetail(idProduct,idOrderBill,quantity,price);
    }
}
