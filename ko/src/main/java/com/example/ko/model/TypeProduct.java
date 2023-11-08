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
@Table(name = "type_product")
public class TypeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_product_id")
    private Long typeProductId;
    @Column(name = "type_product_name")
    private String typeProductName;
    @ManyToOne
    @JoinColumn(name = "type_detail_id",referencedColumnName = "type_detail_id")
    private TypeDetail typeDetail;
}
