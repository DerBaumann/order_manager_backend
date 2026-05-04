package ch.wegmann.pierre.order_manager.place;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/")
    public @ResponseBody List<Place> findAll() {
        return placeService.findAll();
    }

    // TODO: Cleaner solution to handle missing value
    @GetMapping("/{id}")
    public ResponseEntity<Place> findOne(@PathVariable int id) {
        return placeService
                .find(id)
                .map(place -> new ResponseEntity(place, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>((Object) null, HttpStatus.NOT_FOUND));
    }
}
