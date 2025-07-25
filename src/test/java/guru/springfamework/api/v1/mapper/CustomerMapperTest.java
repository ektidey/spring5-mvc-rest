package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    public static final String FIRSTNAME = "Joe";
    public static final String LASTNAME = "Bloggs";
    public static final long ID = 1L;
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() throws Exception {
        Customer category = new Customer();
        category.setFirstname(FIRSTNAME);
        category.setLastname(LASTNAME);
        category.setId(ID);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(category);

        assertEquals(ID, customerDTO.getId());
        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
    }

}