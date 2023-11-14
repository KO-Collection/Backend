package com.example.ko.service.home;

import com.example.ko.dto.home.IProductSearchHome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IHomeService {
    Page<IProductSearchHome> findProductSearchHome(Pageable pageable,String name);
    List<IProductSearchHome> getBestSeller();
    List<IProductSearchHome> getProductNew();

}
