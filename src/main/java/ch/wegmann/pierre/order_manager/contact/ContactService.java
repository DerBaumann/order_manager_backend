package ch.wegmann.pierre.order_manager.contact;

import ch.wegmann.pierre.order_manager.core.EntityNotFoundException;
import ch.wegmann.pierre.order_manager.place.Place;
import ch.wegmann.pierre.order_manager.place.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final PlaceRepository placeRepository;

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    public Contact findByID(Long id) {
        return contactRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id, Contact.class));
    }

    public Contact create(ContactRequestDTO requestDTO) {
        var place = placeRepository
            .findPlaceByPostCode(requestDTO.getPostCode())
            .orElseGet(() -> {
                final var p = new Place(requestDTO.getPlaceName(), requestDTO.getPostCode(), requestDTO.getCanton());
                return placeRepository.save(p);
            });

        final var contact = new Contact(
            requestDTO.getFirstname(),
            requestDTO.getLastname(),
            requestDTO.getEmail(),
            requestDTO.getPhone(),
            requestDTO.getStreet(),
            place
        );
        return contactRepository.save(contact);
    }

    public Contact update(Long id, ContactRequestDTO requestDTO) {
        var place = placeRepository
            .findPlaceByPostCode(requestDTO.getPostCode())
            .map(p -> {
                p.setName(requestDTO.getPlaceName());
                p.setPostCode(requestDTO.getPostCode());
                p.setCanton(requestDTO.getCanton());
                return placeRepository.save(p);
            })
            .orElseGet(() -> {
                final var p = new Place(requestDTO.getPlaceName(), requestDTO.getPostCode(), requestDTO.getCanton());
                return placeRepository.save(p);
            });

        return contactRepository
            .findById(id)
            .map(contact -> {
                contact.setFirstname(requestDTO.getFirstname());
                contact.setLastname(requestDTO.getLastname());
                contact.setEmail(requestDTO.getEmail());
                contact.setPhone(requestDTO.getPhone());
                contact.setPlace(place);
                return contactRepository.save(contact);
            })
            .orElseGet(() -> {
                final var contact = new Contact(
                    requestDTO.getFirstname(),
                    requestDTO.getLastname(),
                    requestDTO.getEmail(),
                    requestDTO.getPhone(),
                    requestDTO.getStreet(),
                    place
                );
                return contactRepository.save(contact);
            });
    };

    public Contact delete(Long id) {
        final var contact = contactRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id, Contact.class));
        contactRepository.delete(contact);
        return contact;
    }
}
