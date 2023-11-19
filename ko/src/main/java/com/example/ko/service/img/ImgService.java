package com.example.ko.service.img;

import com.example.ko.model.Img;
import com.example.ko.repository.IImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImgService implements IImgService{
    @Autowired
    IImgRepository imgRepository;
    @Override
    public List<String> getAllImg(Long idProduct) {
        return imgRepository.findImgByIdProduct(idProduct);
    }
}
