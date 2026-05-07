package ch.wegmann.pierre.order_manager.place;

import ch.wegmann.pierre.order_manager.core.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    public Place find(Long id) {
        return placeRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id, Place.class));
    }
}
