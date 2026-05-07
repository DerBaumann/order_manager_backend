package ch.wegmann.pierre.order_manager.contact;

import ch.wegmann.pierre.order_manager.place.Place;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/*
contacts
  id serial pk
  firstname text
  lastname text
  email text
  phone text
  street text
  place_id int -> places(id)
  created_by int -> users(id)
  created_at timestamp=`now()`
  updated_at timestamp=`now()`
*/

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String firstname;

    @Column(nullable = false)
    @NonNull
    private String lastname;

    @Column(nullable = false)
    @NonNull
    private String email;

    @Column(nullable = false)
    @NonNull
    private String phone;

    @Column(nullable = false)
    @NonNull
    private String street;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    @NonNull
    private Place place;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
