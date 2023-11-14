package com.example.ko.dto.home;

import java.util.Date;

public interface IProductSearchHome {
    Long getIdProduct();

    String getNameProduct();

    Double getPrice();

    String getImg();
    Date getCreatedTime();
}
