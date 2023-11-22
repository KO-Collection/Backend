package com.example.ko.service.cart;
import com.example.ko.dto.home.IProductSearchHome;
import java.util.List;

public interface ICartService {
    void createCard(Long idUser,Long idProduct,Long idSize,Long newQuantity);
    Long quantityProduct(Long id);
    void deleteCart(Long idUser, Long idProduct,Long idSize);
    Long quantityProductCart(Long idProduct, Long idUser,Long idSize);
    List<IProductSearchHome> getAllCart( Long idUser);

}
