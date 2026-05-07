package ch.wegmann.pierre.order_manager.place;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    boolean existsPlaceByPostCode(String postCode);

//    Object findPlaceByPostCode(String postCode);
    Optional<Place> findPlaceByPostCode(String postCode);
}
