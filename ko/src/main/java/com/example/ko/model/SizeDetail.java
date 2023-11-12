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
@Table(name = "size_detail")
public class SizeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sizeDetailId;
    @ManyToOne
    @JoinColumn(name = "size_id",referencedColumnName = "size_id")
    private Size size;
    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "product_id")
    private Product product;
}
