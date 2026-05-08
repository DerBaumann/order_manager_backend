package ch.wegmann.pierre.order_manager.order_position;

import ch.wegmann.pierre.order_manager.core.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderPositionService {
    private final OrderPositionRepository orderPositionRepository;

    public OrderPosition findByID(Long id) {
        return orderPositionRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, OrderPosition.class));
    }

    public OrderPosition update(Long id, OrderPosition orderPosition) {
        return orderPositionRepository
                .findById(id)
                .map(p -> {
                    p.setName(orderPosition.getName());
                    p.setDescription(orderPosition.getDescription());
                    p.setAmount(orderPosition.getAmount());
                    p.setPrice(orderPosition.getPrice());
                    return orderPositionRepository.save(p);
                })
                .orElseGet(() -> orderPositionRepository.save(orderPosition));
    }

    public OrderPosition delete(Long id) {
        final var orderPosition = orderPositionRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, OrderPosition.class));
        orderPositionRepository.delete(orderPosition);
        return orderPosition;
    }
}
