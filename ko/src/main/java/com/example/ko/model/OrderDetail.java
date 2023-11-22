package com.example.ko.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailId;
    @Column(name = "price_order",columnDefinition = "double")
    private Double priceOrder;
    @Column(name = "quantity_order",columnDefinition = "bigint")
    private Long quantityOrder;
    @ManyToOne
    @JoinColumn(name = "order_bill_id",referencedColumnName = "order_bill_id")
    private OrderBill orderBill;
    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "size_id",referencedColumnName = "size_id")
    private Size size;
}
