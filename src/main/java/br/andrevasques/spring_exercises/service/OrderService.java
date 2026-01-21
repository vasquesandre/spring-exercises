package br.andrevasques.spring_exercises.service;

import br.andrevasques.spring_exercises.dto.order.CreateOrderItemRequest;
import br.andrevasques.spring_exercises.dto.order.CreateOrderRequest;
import br.andrevasques.spring_exercises.model.entitites.Client;
import br.andrevasques.spring_exercises.model.entitites.Order;
import br.andrevasques.spring_exercises.model.entitites.Product;
import br.andrevasques.spring_exercises.model.repositories.ClientRepository;
import br.andrevasques.spring_exercises.model.repositories.OrderRepository;
import br.andrevasques.spring_exercises.model.repositories.ProductRepository;
import br.andrevasques.spring_exercises.model.valueObjects.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ClientRepository clientRepository;
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

    private OrderItem getOrderItem(CreateOrderItemRequest createOrderItemRequest, Product product) {
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
        return orderItem;
    }

    public Order createOrder(CreateOrderRequest dto) {
        checkIfClientIdIsNullOrThrow(dto.clientId());
        checkIfSaveItemsListIsNotEmptyOrThrow(dto.items());
        Client client = findClientByIdOrThrow(dto.clientId());
        Order order = new Order(client.getId());
        for (CreateOrderItemRequest createOrderItemRequest : dto.items()) {
            checkIfProductIdIsNullOrThrow(createOrderItemRequest.productId());
            Product product = findProductByIdOrThrow(createOrderItemRequest.productId());
            OrderItem orderItem = getOrderItem(createOrderItemRequest, product);
            order.addItem(orderItem);
        }
        return orderRepository.save(order);
    }

    public Page<Order> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order getOrderById(String orderId) {
        return findOrderByIdOrThrow(orderId);
    }

    public Page<Order> getOrdersByClientId(String clientId, Pageable pageable) {
        return orderRepository.findAllByClientId(clientId, pageable);
    }

    public void deleteOrderById(String orderId) {
        Order order = findOrderByIdOrThrow(orderId);
        orderRepository.delete(order);
    }
}
