package guru.springfamework;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Spring Framework Guru",
		description = "Spring Framework 5: Beginner to Guru",
		version = "1.0",
		termsOfService = "Terms of Service: blah",
		contact = @Contact(name="Estelle Tidey", email = "ektidey@test.com"),
		license = @License(name="Apache Licence Version 2.0", url="https://www.apache.org/licenses/LICENSE-2.0"))
)
public class Spring5MvcRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring5MvcRestApplication.class, args);
	}
}
