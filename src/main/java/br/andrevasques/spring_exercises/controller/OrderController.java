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

    private Product findProductByIdOrThrow(String productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found"));
    }

    private void checkIfClientIdIsNullOrThrow(String clientId) {
        if(clientId == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Client ID is null");
        }
    }

    private void checkIfProductIdIsNullOrThrow(String productId) {
        if(productId == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Product ID is null");
        }
    }

    private void checkIfSaveItemsListIsNotEmptyOrThrow(List<CreateOrderItemRequest> items) {
        if(items == null || items.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "Items list is empty");
        }
    }

    @PostMapping
    public OrderRequest save(@RequestBody CreateOrderRequest dto) {
        checkIfClientIdIsNullOrThrow(dto.clientId());
        checkIfSaveItemsListIsNotEmptyOrThrow(dto.items());
        Client client = findClientByIdOrThrow(dto.clientId());
        Order order = new Order(client.getId());
        for (CreateOrderItemRequest createOrderItemRequest : dto.items()) {
            checkIfProductIdIsNullOrThrow(createOrderItemRequest.productId());
            Product product = findProductByIdOrThrow(createOrderItemRequest.productId());
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
                    checkIfClientIdIsNullOrThrow(order.getClient());
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
