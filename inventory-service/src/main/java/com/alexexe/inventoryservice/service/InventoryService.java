package com.alexexe.inventoryservice.service;

import com.alexexe.inventoryservice.dto.InventoryResponse;
import com.alexexe.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> code){
        return inventoryRepository.findByCodeIn(code).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .code(inventory.getCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();
    }
}
