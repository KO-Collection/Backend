package com.example.ko.repository;

import com.example.ko.dto.home.IProductSearchHome;
import com.example.ko.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "SELECT p.product_id as idProduct,p.product_name as nameProduct,p.price as price,MIN(i.img_url) as img from product p join img i on p.product_id = i.product_id where p.product_name like :name GROUP BY p.product_id", nativeQuery = true)
    Page<IProductSearchHome> findAllProductHome(Pageable pageable,@Param(value = "name") String name);
    @Query(value = "SELECT p.product_id as idProduct,p.product_name as nameProduct,p.price as price,MIN(i.img_url) as img,sum(o.quantity_order) as quantity_total \n" +
            "from product p\n" +
            "join img i on p.product_id = i.product_id \n" +
            "join order_detail o on p.product_id = o.product_id\n" +
            "GROUP BY p.product_id\n" +
            "ORDER BY SUM(o.quantity_order) DESC \n" +
            "limit 10;",nativeQuery = true)
    List<IProductSearchHome> getBestSeller();
    @Query(value = "SELECT p.product_id as idProduct,p.product_name as nameProduct,p.price as price,MIN(i.img_url) as img,p.create_time_product as createdTime \n" +
            "from product p\n" +
            "join img i on p.product_id = i.product_id \n" +
            "WHERE YEAR(p.create_time_product) = YEAR(CURDATE()) " +
            "GROUP BY p.product_id\n" +
            "ORDER BY p.create_time_product DESC \n" +
            "limit 10;",nativeQuery = true)
    List<IProductSearchHome> getProductNew();
}
