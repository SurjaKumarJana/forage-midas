package com.jpmc.midascore.controller;


import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.service.MidasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MidasController {

    @Autowired
    private MidasService service;

    @GetMapping("/balance")
    public ResponseEntity<Balance> getBalance(@RequestParam Long userId ){
        Balance balance = service.getBalance(userId);
        return ResponseEntity.ok().body(balance);
    }
}
