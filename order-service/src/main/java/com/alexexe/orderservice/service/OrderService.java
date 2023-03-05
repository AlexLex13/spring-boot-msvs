package com.alexexe.orderservice.service;

import com.alexexe.orderservice.dto.OrderLineItemsDto;
import com.alexexe.orderservice.dto.OrderRequest;
import com.alexexe.orderservice.model.Order;
import com.alexexe.orderservice.model.OrderLineItems;
import com.alexexe.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        Boolean result = webClient.get()
                .uri("http://localhost:8082/api/inventory")
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (result){
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setCode(orderLineItemsDto.getCode());
        return orderLineItems;
    }
}
