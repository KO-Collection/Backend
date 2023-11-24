package com.example.ko.service.order;

import com.example.ko.dto.home.IHistoryOrder;
import com.example.ko.dto.home.IOrderDetail;
import com.example.ko.repository.IOrderBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    public void createOrderDetail(Long idOrderBill, Long idProduct, Long quantity, Double price, Long idSize) {
        orderBillRepository.createOrderBillDetail(idProduct,idOrderBill,quantity,price,idSize);
    }

    @Override
    public Page<IHistoryOrder> getOrderBillByIdUser(Pageable pageable, String nameUser) {
        return orderBillRepository.getOrderBillByIdUser(pageable,nameUser);
    }

    @Override
    public Page<IOrderDetail> getOrderBillDetail(Pageable pageable, String nameUser, String time) {
        return orderBillRepository.getOrderBillDetail(pageable,nameUser,time);
    }
}
