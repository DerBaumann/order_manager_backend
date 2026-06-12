package ch.wegmann.pierre.order_manager.order;

import ch.wegmann.pierre.order_manager.contact.Contact;
import ch.wegmann.pierre.order_manager.contact.ContactRepository;
import ch.wegmann.pierre.order_manager.core.EntityNotFoundException;
import ch.wegmann.pierre.order_manager.order_position.OrderPosition;
import ch.wegmann.pierre.order_manager.order_position.OrderPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderPositionRepository orderPositionRepository;
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
        final var positions = requestDTO.getPositions().stream().map(positionRequestDTO -> {
            final var position = OrderPosition.fromRequestDTO(positionRequestDTO);
            return orderPositionRepository.save(position);
        }).toList();
        final var order = Order.fromRequestDTO(requestDTO, contact, positions);
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
                .orElseGet(() -> {
                    final var positions = requestDTO.getPositions().stream().map(positionRequestDTO -> {
                        final var position = OrderPosition.fromRequestDTO(positionRequestDTO);
                        return orderPositionRepository.save(position);
                    }).toList();
                    return orderRepository.save(Order.fromRequestDTO(requestDTO, contact, positions));
                });
    }

    ;

    public Order delete(Long id) {
        final var order = orderRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Order.class));
        orderRepository.delete(order);
        return order;
    }
}
