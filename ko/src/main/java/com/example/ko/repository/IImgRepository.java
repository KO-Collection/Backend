package com.example.ko.repository;

import com.example.ko.model.Img;
import com.example.ko.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IImgRepository extends JpaRepository<Img,Long> {
    @Query(value = "SELECT img_url from img where product_id = :idProduct", nativeQuery = true)
    List<String> findImgByIdProduct(@Param(value = "idProduct") Long idProduct);
}
