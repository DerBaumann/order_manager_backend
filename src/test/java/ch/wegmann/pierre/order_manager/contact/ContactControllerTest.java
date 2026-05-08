package ch.wegmann.pierre.order_manager.contact;

import ch.wegmann.pierre.order_manager.place.Place;
import ch.wegmann.pierre.order_manager.place.PlaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback()
public class ContactControllerTest {
    @Autowired
    private MockMvc api;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ObjectMapper mapper;

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
    public void testGetContacts() throws Exception {
        final var token = obtainAccessToken("read", "test");

        final var contact = new Contact(
            "John",
            "Doe",
            "john.doe@example.com",
            "+41 79 111 11 11",
            "Main Street 1",
            testPlace
        );
        contactRepository.save(contact);

        api.perform(
            get("/api/contacts/")
                .header("Authorization", "Bearer " + token)
        )
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("john.doe@example.com")));
    }

    @Test
    public void testGetContact() throws Exception {
        final var token = obtainAccessToken("read", "test");

        final var contact = new Contact(
            "Jane",
            "Smith",
            "jane.smith@example.com",
            "+41 79 222 22 22",
            "Oak Avenue 12",
            testPlace
        );

        contactRepository.save(contact);

        api.perform(
            get(String.format("/api/contacts/%s", contact.getId()))
                .header("Authorization", "Bearer " + token)
        )
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("jane.smith@example.com")));
    }

    @Test
    public void testPostContact() throws Exception {
        final var token = obtainAccessToken("update", "test");

        final var contact = new ContactRequestDTO(
            "Michael",
            "Johnson",
            "michael.johnson@example.com",
            "+41 79 333 33 33",
            "Pine Road 7",
            testPlace.getName(),
            testPlace.getPostCode(),
            testPlace.getCanton()
        );

        api.perform(
            post("/api/contacts/")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(contact))

        )
        .andDo(print()).andExpect(status().isCreated())
        .andExpect(content().string(containsString("michael.johnson@example.com")));
    }

    @Test
    public void testPutContact() throws Exception {
        final var token = obtainAccessToken("update", "test");

        var contact = new Contact(
            "Emily",
            "Brown",
            "emily.brown@example.com",
            "+41 79 444 44 44",
            "Maple Lane 15",
            testPlace
        );
        contactRepository.save(contact);

        final var dto = new ContactRequestDTO(
            "Emily",
            "Brown",
            "e.brown@example.ch",
            "+41 79 444 44 44",
            "Maple Lane 15",
            testPlace.getName(),
            testPlace.getPostCode(),
            testPlace.getCanton()
        );

        contact.setEmail("e.brown@example.ch");

        api.perform(
            put(String.format("/api/contacts/%s", contact.getId()))
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(dto))
        )
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("e.brown@example.ch")));
    }

    @Test
    public void testDeleteContact() throws Exception {
        final var token = obtainAccessToken("test_admin", "test");

        final var contact = new Contact(
            "David",
            "Wilson",
            "david.wilson@example.com",
            "+41 79 555 55 55",
            "Elm Street 20",
            testPlace
        );

        contactRepository.save(contact);

        api.perform(
            delete(String.format("/api/contacts/%s", contact.getId()))
                .header("Authorization", "Bearer " + token)
        )
        .andDo(print()).andExpect(status().isOk());
    }

    private String obtainAccessToken(String username, String password) {
        final var url = "http://localhost:8080/realms/order_manager/protocol/openid-connect/token";
        final var rest = new RestTemplate();

        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final var body = String.format("client_id=order_manager_api&username=%s&password=%s&grant_type=password", username, password);
        final var entity = new HttpEntity<>(body, headers);

        final var response = rest.postForEntity(url, entity, String.class);

        final var jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(response.getBody()).get("access_token").toString();
    }
}
