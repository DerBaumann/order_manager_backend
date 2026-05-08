package ch.wegmann.pierre.order_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// TODO: Proper handling of EntityNotFoundException
// TODO: DTO's for Order and maybe OrderPosition
// TODO: Adjust schema.aml to match current entity setup

@SpringBootApplication
@EnableJpaRepositories
public class OrderManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderManagerApplication.class, args);
	}

}
