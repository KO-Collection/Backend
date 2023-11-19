package com.example.ko.service.cart;
import com.example.ko.dto.home.IProductSearchHome;
import java.util.List;

public interface ICartService {
    void createCard(Long idUser,Long idProduct,Long newQuantity);
    Long quantityProduct(Long id);
    void deleteCart(Long idUser, Long idProduct);
    Long quantityProductCart(Long idProduct, Long idUser);
    List<IProductSearchHome> getAllCart( Long idUser);

}
