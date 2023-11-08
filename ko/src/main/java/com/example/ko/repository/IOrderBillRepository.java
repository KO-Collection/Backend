package com.example.ko.repository;

import com.example.ko.model.OrderBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderBillRepository extends JpaRepository<OrderBill,Long> {

}
