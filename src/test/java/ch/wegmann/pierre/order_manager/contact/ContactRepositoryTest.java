package ch.wegmann.pierre.order_manager.contact;

import ch.wegmann.pierre.order_manager.place.Place;
import ch.wegmann.pierre.order_manager.place.PlaceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ContactRepositoryTest {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private PlaceRepository placeRepository;

    private Place testPlace;

    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();
        placeRepository.deleteAll();

        testPlace = placeRepository.save(new Place(
            "TestPlace",
            "1234",
            "BE"
        ));
    }

    @Test
    void insertContact() {
        var contact1 = contactRepository.save(
            new Contact(
                "John",
                "Doe",
                "john.doe@mail.com",
                "+41 61 123 45 67",
                "Teststreet 5",
                testPlace
            )
        );
        Assertions.assertNotNull(contact1);
        var contact2 = contactRepository.save(
            new Contact(
                "Jane",
                "Doe",
                "jane.doe@mail.com",
                "+41 61 420 69 67",
                "Teststreet 666",
                testPlace
            )
        );
        Assertions.assertNotNull(contact2);
    }
}
