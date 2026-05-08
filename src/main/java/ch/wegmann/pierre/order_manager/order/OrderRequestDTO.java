package ch.wegmann.pierre.order_manager.order;

import ch.wegmann.pierre.order_manager.order_position.OrderPositionRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequestDTO {
    private String name;
    private String description;
    private OrderStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private OrderPriority priority;
    private String category;
    private List<OrderPositionRequestDTO> positions;
    private Long contactId;
}
