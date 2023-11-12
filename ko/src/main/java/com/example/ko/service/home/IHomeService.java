package com.example.ko.service.home;

import com.example.ko.dto.home.IProductSearchHome;

import java.util.List;

public interface IHomeService {
    List<IProductSearchHome> findProductSearchHome(String name);
    List<IProductSearchHome> getBestSeller();

}
