package ch.wegmann.pierre.order_manager.place;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "places")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(name = "post_code", nullable = false, length = 4)
    @NonNull
    private String postCode;

    @Column(nullable = false)
    @NonNull
    private String canton;
}