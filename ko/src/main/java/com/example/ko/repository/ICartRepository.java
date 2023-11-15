package com.example.ko.repository;

import com.example.ko.dto.home.IProductSearchHome;
import com.example.ko.model.Cart;
import com.example.ko.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICartRepository extends JpaRepository<Cart,Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into ko_collection.cart (user_id,product_id,quantity_order ) VALUES (:id_user, :id_product, :newQuantity) ON DUPLICATE KEY UPDATE quantity_order = quantity_order + :newQuantity")
    void createCard(@Param("id_user") Long idUser, @Param("id_product") Long idProduct, @Param("newQuantity") Long newQuantity);
    @Query(value =" select quantity from ko_collection.product where product_id = :id",nativeQuery = true)
    Long quantityProduct(@Param("id") Long id);
    @Query(value =" select c.quantity_order  from cart c where c.product_id = :product_id and c.user_id = :user_id",nativeQuery = true)
    Long quantityProductCart(@Param("product_id") Long idProduct,@Param("user_id") Long idUser);
    @Query(value = "delete from cart where id_user = :id",nativeQuery = true)
    void deleteCart(@Param("id") Long idUser);
    @Query(value = "SELECT  p.product_id as idProduct,p.product_name as nameProduct,p.price as price,MIN(i.img_url) as img ,c.quantity_order as quantityOrder\n" +
            "from cart c \n" +
            " join product p on p.product_id = c.product_id\n" +
            " join img i on p.product_id = i.product_id\n" +
            " where user_id = :user_id\n" +
            " group by p.product_id",nativeQuery = true)
    List<IProductSearchHome> getAllCart( @Param("user_id") Long idUser);
}
