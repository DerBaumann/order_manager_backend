package ch.wegmann.pierre.order_manager.order;

import ch.wegmann.pierre.order_manager.contact.Contact;
import ch.wegmann.pierre.order_manager.order_position.OrderPosition;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/*
orders
  id serial pk
  name text
  description text
  status status(open, in_progress, finished, cancelled)
  start_date date
  end_date date
  priority priority(low, medium, high)
  category text
  contact_id int -> contacts(id)
  created_by int -> users(id)
  created_at timestamp=`now()`
  updated_at timestamp=`now()`
 */

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(nullable = false)
    @NonNull
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private OrderStatus status;

    @Column(nullable = false)
    @NonNull
    private LocalDate startDate;

    @Column(nullable = false)
    @NonNull
    private LocalDate endDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private OrderPriority priority;

    @Column(nullable = false)
    @NonNull
    private String category;

    @OneToMany
    @JoinColumn(name = "order_id")
    @NonNull
    private List<OrderPosition> positions;

    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    @NonNull
    private Contact contact;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
