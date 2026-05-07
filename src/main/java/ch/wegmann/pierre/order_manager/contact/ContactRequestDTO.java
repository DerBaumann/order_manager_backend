package ch.wegmann.pierre.order_manager.contact;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactRequestDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String street;
    private String placeName;
    private String postCode;
    private String canton;
}
