package br.andrevasques.spring_exercises.controller;

import br.andrevasques.spring_exercises.dto.order.CreateOrderRequest;
import br.andrevasques.spring_exercises.dto.order.OrderResponse;
import br.andrevasques.spring_exercises.model.entitites.Client;
import br.andrevasques.spring_exercises.model.entitites.Order;
import br.andrevasques.spring_exercises.service.ClientService;
import br.andrevasques.spring_exercises.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientService clientService;

    private OrderResponse toOrderResponse(Order order) {
        Client client = clientService.getClientById(order.getClient());
        return new OrderResponse(order, client);
    }

    @PostMapping
    public OrderResponse createOrder(@RequestBody @Valid CreateOrderRequest dto) {
        Order order = orderService.createOrder(dto);
        return toOrderResponse(order);
    }

    @GetMapping
    public Page<OrderResponse> getOrders(Pageable pageable) {
        return orderService.getOrders(pageable)
                .map(this::toOrderResponse);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable String id) {
        Order order = orderService.getOrderById(id);
        return toOrderResponse(order);
    }

    @GetMapping("/client")
    public Page<OrderResponse> getOrdersByClientId(@RequestParam String clientId, Pageable pageable) {
        return orderService.getOrdersByClientId(clientId, pageable)
                .map(this::toOrderResponse);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        orderService.deleteOrderById(id);
    }

}
