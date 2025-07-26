package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.CustomerService;
import guru.springfamework.services.ResourceNotFoundException;
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
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
            .setControllerAdvice(new RestResponseEntityExceptionHandler())
            .build();
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
        customer1.setCustomerUrl(CustomerController.BASE_URL + "/1");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(2L);
        customer2.setFirstname("Jane");
        customer2.setLastname("Smith");
        customer2.setCustomerUrl(CustomerController.BASE_URL + "/2");

        Mockito.when(customerService.getAllCustomers()).thenReturn(List.of(customer1, customer2));

        mockMvc.perform(get(CustomerController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setId(1L);
        customer.setFirstname(JOE);
        customer.setLastname(BLOGGS);
        customer.setCustomerUrl(CustomerController.BASE_URL + "/1");

        Mockito.when(customerService.getCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(get(CustomerController.BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstname", equalTo(JOE)))
            .andExpect(jsonPath("$.lastname", equalTo(BLOGGS)))
            .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
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
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        Mockito.when(customerService.createNewCustomer(customerDTO)).thenReturn(returnDTO);

        mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.firstname", equalTo(JOE)))
            .andExpect(jsonPath("$.lastname", equalTo(BLOGGS)))
            .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
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
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        Mockito.when(customerService.saveCustomer(anyLong(), eq(customerDTO))).thenReturn(returnDTO);

        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstname", equalTo(JOE)))
            .andExpect(jsonPath("$.lastname", equalTo(BLOGGS)))
            .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void testPatchCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(JOE);
        customerDTO.setLastname(BLOGGS);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setId(1L);
        returnDTO.setFirstname(customerDTO.getFirstname());
        returnDTO.setLastname(customerDTO.getLastname());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        Mockito.when(customerService.patchCustomer(anyLong(), eq(customerDTO))).thenReturn(returnDTO);

        mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstname", equalTo(JOE)))
            .andExpect(jsonPath("$.lastname", equalTo(BLOGGS)))
            .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void testDeleteCustomer() throws Exception {

        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        Mockito.verify(customerService, Mockito.times(1)).deleteCustomerById(1L);

    }

    @Test
    public void testGetByNameNotFound() throws Exception {

        Mockito.when(customerService.getCustomerById(1L)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

}