package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Vendor;
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

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class VendorServiceITest {
    private static final String UPDATED_NAME = "UpdatedName";

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    VendorService vendorService;

    @BeforeEach
    public void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void patchVendorUpdateName() {
        long id = getVendorIdValue();

        Vendor originalVendor  = vendorRepository.getReferenceById(id);
        assertThat(originalVendor).isNotNull();

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(UPDATED_NAME);

        vendorService.patchVendor(id, vendorDTO);

        Vendor updatedVendor = vendorRepository.findById(id).orElse(null);
        assertThat(updatedVendor).isNotNull();
        assertThat(updatedVendor.getName()).isEqualTo(UPDATED_NAME);
    }

    private Long getVendorIdValue() {
        List<Vendor> vendors = vendorRepository.findAll();
        return vendors.get(0).getId();
    }

}
