package com.example.ko.dto.home;

import com.example.ko.model.Size;

import java.util.Date;
import java.util.List;

public interface IProductSearchHome {
    Long getIdProduct();

    String getNameProduct();

    Double getPrice();

    String getImg();
    Date getCreatedTime();
    Long getQuantityOrder();
    String getSizeProduct();
    Long getIdSizeProduct();
    Long getIdColor();
    String getColorName();
    Long getIdType();

}
