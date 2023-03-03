package com.alexexe.inventoryservice.controller;

import com.alexexe.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping("/{code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("code") String code){
        return inventoryService.isInStock(code);
    }
}
