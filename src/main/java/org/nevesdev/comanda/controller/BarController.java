package org.nevesdev.comanda.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.nevesdev.comanda.model.bar.Bar;
import org.nevesdev.comanda.service.interfaces.BarServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bar")
public class BarController {

    @Autowired
    private BarServiceInterface barServiceInterface;


    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Bar> getById(@RequestParam Long id) {
        return ResponseEntity.status(200).body(barServiceInterface.findBarById(id));
    }

}
