package com.example.ko.repository;

import com.example.ko.dto.home.IHistoryOrder;
import com.example.ko.model.OrderBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IOrderBillRepository extends JpaRepository<OrderBill,Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO ko_collection.order_bill ( day_of_order, flag_delete,time_of_order, user_id) VALUES ( :day_of_order, false,:time_of_order, :user_id)")
    void createOrderBill(@Param("user_id") Long idUser, @Param("day_of_order")LocalDate dayOrder, @Param("time_of_order")LocalDateTime timeOrder);
    @Query(value ="SELECT max(order_bill_id) FROM ko_collection.order_bill;",nativeQuery = true)
    Long getIdBestNew();
    @Query(value =" select  ob.day_of_order AS dayOrder, \n" +
            "    ob.day_of_order AS timeOrder, \n" +
            "    COUNT(ob.order_bill_id) AS numberOrder, \n" +
            "    SUM(od.quantity_order) AS quantityOrder, \n" +
            "    SUM(od.quantity_order * od.price_order) AS totalMoney\n" +
            " from order_bill ob\n" +
            " join order_detail od on ob.order_bill_id = od.order_bill_id \n" +
            " join users s on ob.user_id = s.user_id \n" +
            "where s.user_name like :user_name \n" +
            " group by ob.day_of_order ",nativeQuery = true)
    Page<IHistoryOrder> getOrderBillByIdUser(Pageable pageable ,@Param(value = "user_name")String nameUser);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = " INSERT INTO ko_collection.order_detail ( product_id, order_bill_id,quantity_order, price_order,size_id) VALUES ( :product_id, :order_bill_id,:quantity_order, :price_order, :size_id) ")
    void createOrderBillDetail(@Param("product_id") Long idProduct, @Param("order_bill_id")Long idOrderBill, @Param("quantity_order")Long quantity,@Param("price_order")Double price,@Param("size_id")Long idSize);

}
