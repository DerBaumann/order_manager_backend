package ch.wegmann.pierre.order_manager.order;

import ch.wegmann.pierre.order_manager.order_position.OrderPositionService;
import ch.wegmann.pierre.order_manager.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/")
    @RolesAllowed({Roles.Read, Roles.Update, Roles.Admin})
    public @ResponseBody List<Order> index() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    @RolesAllowed({Roles.Read, Roles.Update, Roles.Admin})
    public @ResponseBody Order show(@PathVariable Long id) {
        return orderService.findByID(id);
    }

    @PostMapping("/")
    @RolesAllowed({Roles.Update, Roles.Admin})
    public ResponseEntity<Order> store(@RequestBody @Valid Order order) {
        return new ResponseEntity<>(orderService.create(order), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @RolesAllowed({Roles.Update, Roles.Admin})
    public @ResponseBody Order update(@RequestBody @Valid Order order, @PathVariable Long id) {
        return orderService.update(id, order);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({Roles.Admin})
    public @ResponseBody Order destroy(@PathVariable Long id) {
        return orderService.delete(id);
    }
}
