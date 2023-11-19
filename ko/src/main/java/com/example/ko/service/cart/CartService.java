package com.example.ko.service.cart;

import com.example.ko.dto.home.IProductSearchHome;
import com.example.ko.repository.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService implements ICartService {
    @Autowired
    ICartRepository cartRepository;

    @Override
    public void createCard(Long idUser, Long idProduct, Long newQuantity) {
        cartRepository.createCard(idUser, idProduct, newQuantity);
    }

    @Override
    public Long quantityProduct(Long id) {
        return cartRepository.quantityProduct(id);
    }

    @Override
    public void deleteCart(Long idUser,Long idProduct) {
        cartRepository.deleteCart(idUser,idProduct);
    }

    @Override
    public Long quantityProductCart(Long idProduct, Long idUser) {
        return cartRepository.quantityProductCart(idProduct,idUser);
    }

    @Override
    public List<IProductSearchHome> getAllCart(Long idUser) {
        return cartRepository.getAllCart(idUser);
    }
}
