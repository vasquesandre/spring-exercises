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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping
    public OrderRequest save(@RequestBody CreateOrderRequest dto) {
        if(dto.clientId() == null) {
            throw new ResponseStatusException(NOT_FOUND, "Client ID is null");
        }
        Client client = clientRepository.findById(dto.clientId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Client not found."));
        List<OrderItem> items =  new ArrayList<>();
        Order order = new Order(client, items);
        for (CreateOrderItemRequest createOrderItemRequest : dto.items()) {
            if(createOrderItemRequest.productId() == null) {
                throw new ResponseStatusException(NOT_FOUND, "Product ID is null.");
            }
            Product product = productRepository.findById(createOrderItemRequest.productId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found."));
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setName(product.getName());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(createOrderItemRequest.quantity());
            items.add(orderItem);
        }
        orderRepository.save(order);
        return new OrderRequest(
                order.getId(),
                order.getClient(),
                order.getItems()
        );
    }

}
