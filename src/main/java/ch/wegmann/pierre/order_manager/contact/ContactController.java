package ch.wegmann.pierre.order_manager.contact;

import ch.wegmann.pierre.order_manager.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @GetMapping("/")
    @RolesAllowed({Roles.Read, Roles.Update, Roles.Admin})
    public @ResponseBody List<Contact> index() {
        return contactService.findAll();
    }

    @GetMapping("/{id}")
    @RolesAllowed({Roles.Read, Roles.Update, Roles.Admin})
    public @ResponseBody Contact show(@PathVariable Long id) {
        return contactService.findByID(id);
    }

    @PostMapping("/")
    @RolesAllowed({Roles.Update, Roles.Admin})
    public ResponseEntity<Contact> store(@RequestBody @Valid ContactRequestDTO requestDTO) {
        return new ResponseEntity<>(contactService.create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @RolesAllowed({Roles.Update, Roles.Admin})
    public @ResponseBody Contact update(@RequestBody @Valid ContactRequestDTO requestDTO, @PathVariable Long id) {
        return contactService.update(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({Roles.Update, Roles.Admin})
    public @ResponseBody Contact destroy(@PathVariable Long id) {
        return contactService.delete(id);
    }
}
