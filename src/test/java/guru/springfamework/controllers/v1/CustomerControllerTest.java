package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends AbstractRestControllerTest {

    public static final String JOE = "Joe";
    public static final String BLOGGS = "Bloggs";
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    AutoCloseable mocks;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    public void testListCustomers() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(1L);
        customer1.setFirstname(JOE);
        customer1.setLastname(BLOGGS);
        customer1.setCustomerUrl("/api/v1/customer/1");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(2L);
        customer2.setFirstname("Jane");
        customer2.setLastname("Smith");
        customer2.setCustomerUrl("/api/v1/customer/2");

        Mockito.when(customerService.getAllCustomers()).thenReturn(List.of(customer1, customer2));

        mockMvc.perform(get("/api/v1/customers").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setId(1L);
        customer.setFirstname(JOE);
        customer.setLastname(BLOGGS);
        customer.setCustomerUrl("/api/v1/customer/1");

        Mockito.when(customerService.getCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(get("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstname", equalTo(JOE)))
            .andExpect(jsonPath("$.lastname", equalTo(BLOGGS)))
            .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customer/1")));
    }

    @Test
    public void testCreateNewCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(JOE);
        customerDTO.setLastname(BLOGGS);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setId(1L);
        returnDTO.setFirstname(customerDTO.getFirstname());
        returnDTO.setLastname(customerDTO.getLastname());
        returnDTO.setCustomerUrl("/api/v1/customer/1");

        Mockito.when(customerService.createNewCustomer(customerDTO)).thenReturn(returnDTO);

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.firstname", equalTo(JOE)))
            .andExpect(jsonPath("$.lastname", equalTo(BLOGGS)))
            .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customer/1")));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(JOE);
        customerDTO.setLastname(BLOGGS);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setId(1L);
        returnDTO.setFirstname(customerDTO.getFirstname());
        returnDTO.setLastname(customerDTO.getLastname());
        returnDTO.setCustomerUrl("/api/v1/customer/1");

        Mockito.when(customerService.saveCustomer(anyLong(), eq(customerDTO))).thenReturn(returnDTO);

        mockMvc.perform(put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstname", equalTo(JOE)))
            .andExpect(jsonPath("$.lastname", equalTo(BLOGGS)))
            .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customer/1")));
    }

}