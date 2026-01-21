package br.andrevasques.spring_exercises.controller;

import br.andrevasques.spring_exercises.dto.order.CreateOrderItemRequest;
import br.andrevasques.spring_exercises.dto.order.CreateOrderRequest;
import br.andrevasques.spring_exercises.dto.order.OrderRequest;
import br.andrevasques.spring_exercises.model.entitites.Client;
import br.andrevasques.spring_exercises.model.entitites.Order;
import br.andrevasques.spring_exercises.model.entitites.Product;
import br.andrevasques.spring_exercises.model.repositories.ClientRepository;
import br.andrevasques.spring_exercises.model.repositories.OrderRepository;
import br.andrevasques.spring_exercises.model.repositories.ProductRepository;
import br.andrevasques.spring_exercises.model.valueObjects.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    private Client findClientByIdOrThrow(String clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Client not found"));
    }

    private Order findOrderByIdOrThrow(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Order not found"));
    }

    @PostMapping
    public OrderRequest save(@RequestBody CreateOrderRequest dto) {
        if(dto.clientId() == null || dto.items().isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "Client ID is null or items list is empty");
        }
        Client client = findClientByIdOrThrow(dto.clientId());
        Order order = new Order(client.getId());
        for (CreateOrderItemRequest createOrderItemRequest : dto.items()) {
            if(createOrderItemRequest.productId() == null) {
                throw new ResponseStatusException(BAD_REQUEST, "Product ID is null");
            }
            Product product = productRepository.findById(createOrderItemRequest.productId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found."));
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setName(product.getName());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(createOrderItemRequest.quantity());
            orderItem.setDiscount(product.getDiscount());
            BigDecimal finalPrice =
                    product.getFinalPrice()
                            .multiply(BigDecimal.valueOf(createOrderItemRequest.quantity()));
            orderItem.setFinalPrice(finalPrice);
            order.addItem(orderItem);
        }
        orderRepository.save(order);
        return new OrderRequest(
                order.getId(),
                client,
                order.getItems()
        );
    }

    @GetMapping
    public Page<OrderRequest> getOrders() {
        Pageable pageable = PageRequest.of(0, 10);

        return orderRepository.findAll(pageable)
                .map(order -> {
                    Client client = findClientByIdOrThrow(order.getClient());
                    return new OrderRequest(
                            order.getId(),
                            client,
                            order.getItems()
                    );
                });
    }

    @GetMapping("/{id}")
    public OrderRequest getOrderById(@PathVariable String id) {
        Order order = findOrderByIdOrThrow(id);
        Client client = findClientByIdOrThrow(order.getClient());
        return new OrderRequest(
                order.getId(),
                client,
                order.getItems()
        );
    }

    @GetMapping("/client")
    public Page<OrderRequest> getOrdersByClientId(@RequestParam String clientId) {
        Pageable pageable = PageRequest.of(0, 10);
        return orderRepository.findAllByClientId(clientId, pageable)
                .map(order -> {
                    Client client = findClientByIdOrThrow(order.getClient());

                    return new OrderRequest(
                            order.getId(),
                            client,
                            order.getItems()
                    );
                });
    }

    @DeleteMapping
    public void delete(@RequestParam String id) {
        Order order = findOrderByIdOrThrow(id);
        orderRepository.delete(order);
    }

}
