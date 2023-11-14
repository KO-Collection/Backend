package com.example.ko.service.home;

import com.example.ko.dto.home.IProductSearchHome;
import com.example.ko.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService implements IHomeService{
    @Autowired
    IProductRepository productRepository;

    @Override
    public Page<IProductSearchHome> findProductSearchHome(Pageable pageable,String name) {
        return productRepository.findAllProductHome(pageable,"%"+name+"%");
    }


    @Override
    public List<IProductSearchHome> getBestSeller() {
        return productRepository.getBestSeller();
    }

    @Override
    public List<IProductSearchHome> getProductNew() {
        return productRepository.getProductNew();
    }
}
