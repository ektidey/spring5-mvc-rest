package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerServiceITest {

    private static final String UPDATED_NAME = "UpdatedName";

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;

    @BeforeEach
    public void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void patchCustomerUpdateFirstName() {
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getReferenceById(id);
        assertNotNull(originalCustomer);

        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(UPDATED_NAME);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).orElse(null);
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.getFirstname()).isEqualTo(UPDATED_NAME);
        assertThat(updatedCustomer.getLastname()).isEqualTo(originalLastName);
    }

    @Test
    public void patchCustomerUpdateLastName() {
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getReferenceById(id);
        assertNotNull(originalCustomer);

        String originalFirstName = originalCustomer.getFirstname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(UPDATED_NAME);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).orElse(null);
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.getFirstname()).isEqualTo(originalFirstName);
        assertThat(updatedCustomer.getLastname()).isEqualTo(UPDATED_NAME);
    }

    private Long getCustomerIdValue() {
        List<Customer> customers = customerRepository.findAll();
        return customers.get(0).getId();
    }

}
