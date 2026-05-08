package ch.wegmann.pierre.order_manager.order;

import ch.wegmann.pierre.order_manager.contact.Contact;
import ch.wegmann.pierre.order_manager.contact.ContactRepository;
import ch.wegmann.pierre.order_manager.core.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ContactRepository contactRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findByID(Long id) {
        return orderRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id, Order.class));
    }

    public Order create(OrderRequestDTO requestDTO) {
        final var contact = contactRepository
            .findById(requestDTO.getContactId())
            .orElseThrow(() -> new EntityNotFoundException(requestDTO.getContactId(), Contact.class));
        final var order = Order.fromRequestDTO(requestDTO, contact);
        return orderRepository.save(order);
    }

    public Order update(Long id, OrderRequestDTO requestDTO) {
        final var contact = contactRepository
                .findById(requestDTO.getContactId())
                .orElseThrow(() -> new EntityNotFoundException(requestDTO.getContactId(), Contact.class));
        return orderRepository
            .findById(id)
            .map(o -> {
                o.setName(requestDTO.getName());
                o.setDescription(requestDTO.getDescription());
                o.setStatus(requestDTO.getStatus());
                o.setStartDate(requestDTO.getStartDate());
                o.setEndDate(requestDTO.getEndDate());
                o.setPriority(requestDTO.getPriority());
                o.setContact(contact);
                return orderRepository.save(o);
            })
            .orElseGet(() -> orderRepository.save(Order.fromRequestDTO(requestDTO, contact)));
    };

    public Order delete(Long id) {
        final var order = orderRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id, Order.class));
        orderRepository.delete(order);
        return order;
    }
}
