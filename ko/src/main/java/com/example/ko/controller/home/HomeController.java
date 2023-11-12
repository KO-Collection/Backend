package com.example.ko.controller.home;

import com.example.ko.dto.home.IProductSearchHome;
import com.example.ko.service.home.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private IHomeService homeService;
    @GetMapping("/bestsellers")
    public ResponseEntity<List<IProductSearchHome>> getBestsellers() {
        List<IProductSearchHome> getBestsellers = homeService.getBestSeller();
        if (getBestsellers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(getBestsellers, HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity<List<IProductSearchHome>> getListByName(@RequestParam(value = "name",defaultValue = "",required = false)String name){
        List<IProductSearchHome> getListByName = homeService.findProductSearchHome(name);
        if (getListByName.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(getListByName, HttpStatus.OK);
    }
}
