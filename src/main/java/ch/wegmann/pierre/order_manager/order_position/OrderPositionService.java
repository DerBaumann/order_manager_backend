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

    public OrderPosition update(Long id, OrderPositionRequestDTO requestDto) {
        return orderPositionRepository
            .findById(id)
            .map(p -> {
                p.setName(requestDto.getName());
                p.setDescription(requestDto.getDescription());
                p.setAmount(requestDto.getAmount());
                p.setPrice(requestDto.getPrice());
                return orderPositionRepository.save(p);
            })
            .orElseGet(() -> orderPositionRepository.save(OrderPosition.fromRequestDTO(requestDto)));
    }

    public OrderPosition delete(Long id) {
        final var orderPosition = orderPositionRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id, OrderPosition.class));
        orderPositionRepository.delete(orderPosition);
        return orderPosition;
    }
}
