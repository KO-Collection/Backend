package com.example.ko.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    private String code;
    @Column(name = "product_name")
    private String productName;
    @Column(columnDefinition = "double")
    private Double price;
    @Column(columnDefinition = "bigint")
    private Long quantity;
    @Column(name = "description_product",columnDefinition = "longtext")
    private String descriptionProduct;
    @Column(name = "create_time_product")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdTime;
    @ManyToOne
    @JoinColumn(name = "origin_id",referencedColumnName = "origin_id")
    private Origin origin;
    @ManyToOne
    @JoinColumn(name = "color_product_id",referencedColumnName = "color_product_id")
    private ColorProduct colorProduct;
    @ManyToOne
    @JoinColumn(name = "type_product_id",referencedColumnName = "type_product_id")
    private TypeProduct typeProduct;


}
