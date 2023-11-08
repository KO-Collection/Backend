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
@Table(name = "order_bill")
public class OrderBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_bill_id")
    private Long orderBillId;
    @Column(name = "time_of_order",columnDefinition = "time")
    private String timeOfOrder;
    @Column(name = "day_of_order",columnDefinition = "date")
    private String dayOfOrder;
    @Column(name = "flag_delete")
    private Boolean flagDelete;
    @Column(columnDefinition = "text")
    private String note;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private Users user;
}
