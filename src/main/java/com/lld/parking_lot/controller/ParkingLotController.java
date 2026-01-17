package com.lld.parking_lot.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;





@RestController
@RequestMapping("/parking-lot")
@Log4j2
public class ParkingLotController {


    @GetMapping("/hello")
    public String sayHello() {
        log.info("Hello World hit");
        return "<b>Hello World</b>";
    }
}
