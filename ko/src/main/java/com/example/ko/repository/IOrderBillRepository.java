package com.example.ko.repository;

import com.example.ko.model.OrderBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IOrderBillRepository extends JpaRepository<OrderBill,Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO ko_collection.order_bill ( day_of_order, flag_delete,time_of_order, user_id) VALUES ( :day_of_order, false,:time_of_order, :user_id)")
    void createOrderBill(@Param("user_id") Long idUser, @Param("day_of_order")LocalDate dayOrder, @Param("time_of_order")LocalDateTime timeOrder);
    @Query(value ="SELECT max(order_bill_id) FROM ko_collection.order_bill;",nativeQuery = true)
    Long getIdBestNew();
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO ko_collection.order_detail ( product_id, order_bill_id,quantity_order, price_order) VALUES ( :product_id, :order_bill_id,:quantity_order, :price_order)")
    void createOrderBillDetail(@Param("product_id") Long idProduct, @Param("order_bill_id")Long idOrderBill, @Param("quantity_order")Long quantity,@Param("price_order")Double price);

}
