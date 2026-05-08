package ch.wegmann.pierre.order_manager.order_position;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order_positions")
public class OrderPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    @NonNull
    private Integer amount;

    @Column(nullable = false)
    @NonNull
    private BigDecimal price;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public OrderPosition(
        @NonNull String name,
        String description,
        @NonNull Integer amount,
        @NonNull BigDecimal price
    ) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.price = price;
    }

    public static OrderPosition fromRequestDTO(OrderPositionRequestDTO requestDTO) {
        return new OrderPosition(
            requestDTO.getName(),
            requestDTO.getDescription(),
            requestDTO.getAmount(),
            requestDTO.getPrice()
        );
    }
}
