package ch.wegmann.pierre.order_manager.order_position;

import ch.wegmann.pierre.order_manager.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@RestController
@RequestMapping("/api/orders/{orderId}/positions")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class OrderPositionController {
    private final OrderPositionService orderPositionService;

    @GetMapping("/{id}")
    @RolesAllowed({Roles.Read, Roles.Update, Roles.Admin})
    public @ResponseBody OrderPosition show(@PathVariable Long id) {
        return orderPositionService.findByID(id);
    }

    @PutMapping("/{id}")
    @RolesAllowed({Roles.Update, Roles.Admin})
    public @ResponseBody OrderPosition update(@PathVariable Long id, @RequestBody OrderPosition orderPosition) {
        return orderPositionService.update(id, orderPosition);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({Roles.Update, Roles.Admin})
    public @ResponseBody OrderPosition delete(@PathVariable Long id) {
        return orderPositionService.delete(id);
    }
}
