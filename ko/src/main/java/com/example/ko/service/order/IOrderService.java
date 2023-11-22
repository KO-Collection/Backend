package com.example.ko.service.order;

import com.example.ko.dto.home.IHistoryOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {
    void deleteOrder(Long idUser,Long idOrderBill);
    Long createOrderBill(Long idUser);
    void createOrderDetail(Long idOrderBill,Long idProduct,Long quantity,Double price,Long idSize);
    Page<IHistoryOrder> getOrderBillByIdUser(Pageable pageable,String nameUser);

}
