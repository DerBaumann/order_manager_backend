package ch.wegmann.pierre.order_manager.place;

import ch.wegmann.pierre.order_manager.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/places")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/")
    @RolesAllowed({Roles.Read, Roles.Update, Roles.Admin})
    public @ResponseBody List<Place> findAll() {
        return placeService.findAll();
    }

    @GetMapping("/{id}")
    @RolesAllowed({Roles.Read, Roles.Update, Roles.Admin})
    public @ResponseBody Place findOne(@PathVariable long id) {
        return placeService.find(id);
    }
}
