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
    @Query(nativeQuery = true, value = "insert into ko_collection.cart (user_id,product_id,size_id,quantity_order ) VALUES (:id_user, :id_product,:size_id, :newQuantity) ON DUPLICATE KEY UPDATE quantity_order = quantity_order + :newQuantity")
    void createCard(@Param("id_user") Long idUser, @Param("id_product") Long idProduct,@Param("size_id") Long idSize,@Param("newQuantity") Long newQuantity);
    @Query(value =" select quantity from ko_collection.product where product_id = :id",nativeQuery = true)
    Long quantityProduct(@Param("id") Long id);
    @Query(value =" select c.quantity_order  from cart c where c.product_id = :product_id and c.user_id = :user_id and size_id = :size_id",nativeQuery = true)
    Long quantityProductCart(@Param("product_id") Long idProduct,@Param("user_id") Long idUser,@Param(value = "size_id") Long idSize);
    @Transactional
    @Modifying
    @Query(value = "delete from cart where user_id = :id_user and product_id = :product_id and size_id = :size_id",nativeQuery = true)
    void deleteCart(@Param("id_user") Long idUser,@Param("product_id") Long idProduct,@Param(value = "size_id") Long idSize);
    @Query(value = "SELECT  p.product_id as idProduct,cl.color_product_name as colorName,p.product_name as nameProduct,p.price as price,MIN(i.img_url) as img,s.size_name as sizeProduct,s.size_id as idSizeProduct,c.quantity_order as quantityOrder\n" +
            "from cart c \n" +
            " join product p on p.product_id = c.product_id\n" +
            " join img i on p.product_id = i.product_id\n" +
            " join color_product cl on p.color_product_id = cl.color_product_id\n" +
            " join size s on c.size_id = s.size_id\n" +
            " where user_id = :user_id\n" +
            " group by p.product_id,p.product_name,c.size_id",nativeQuery = true)
    List<IProductSearchHome> getAllCart( @Param("user_id") Long idUser);

}
