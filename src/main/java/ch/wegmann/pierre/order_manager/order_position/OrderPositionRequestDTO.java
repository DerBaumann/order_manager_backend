package ch.wegmann.pierre.order_manager.order_position;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderPositionRequestDTO {
    private String name;
    private String description;
    private Integer amount;
    private BigDecimal price;
}
