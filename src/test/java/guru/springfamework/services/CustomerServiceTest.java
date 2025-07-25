package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapperImpl;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerServiceTest {

    private static final String FIRSTNAME = "Joe";
    private static final String LASTNAME = "Bloggs";

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    AutoCloseable mocks;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(new CustomerMapperImpl(), customerRepository);
    }

    @AfterEach
    public void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    public void getAllCustomers() {

        Mockito.when(customerRepository.findAll())
            .thenReturn(List.of(new Customer(), new Customer(), new Customer()));

        List<CustomerDTO> customers = customerService.getAllCustomers();

        assertEquals(3, customers.size());
    }

    @Test
    public void getCustomerById() {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);

        Mockito.when(customerRepository.findById(1L))
            .thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        assertEquals(1L, customerDTO.getId());
        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
        assertEquals("/api/v1/customer/1", customerDTO.getCustomerUrl());

    }

}