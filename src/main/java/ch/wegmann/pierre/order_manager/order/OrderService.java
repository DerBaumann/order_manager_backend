package ch.wegmann.pierre.order_manager.order;

import ch.wegmann.pierre.order_manager.core.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findByID(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Order.class));
    }

    public Order create(Order order) {
        return orderRepository.save(order);
    }

    public Order update(Long id, Order order) {
        return orderRepository
                .findById(id)
                .map(o -> {
                    o.setName(order.getName());
                    o.setDescription(order.getDescription());
                    o.setStatus(order.getStatus());
                    o.setStartDate(order.getStartDate());
                    o.setEndDate(order.getEndDate());
                    o.setPriority(order.getPriority());
                    o.setContact(order.getContact());
                    return orderRepository.save(o);
                })
                .orElseGet(() -> orderRepository.save(order));
    };

    public Order delete(Long id) {
        final var order = orderRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Order.class));
        orderRepository.delete(order);
        return order;
    }
}
