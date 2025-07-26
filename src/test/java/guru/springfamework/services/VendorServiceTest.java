package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class VendorServiceTest {

    private static final String NAME = "Test";

    @Mock
    private VendorRepository vendorRepository;

    private VendorService vendorService;

    private AutoCloseable mocks;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @AfterEach
    public void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    public void getAllVendors() {
        BDDMockito.given(vendorRepository.findAll()).willReturn(List.of(new Vendor(), new Vendor(), new Vendor()));

        List<VendorDTO> vendors = vendorService.getAllVendors();

        BDDMockito.then(vendorRepository).should(Mockito.times(1)).findAll();
        assertThat(vendors).hasSize(3);
    }

    @Test
    public void getVendorById() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName(NAME);

        Mockito.when(vendorRepository.findById(1L))
            .thenReturn(Optional.of(vendor));

        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        assertThat(vendorDTO.getName()).isEqualTo(NAME);
        assertThat(vendorDTO.getVendorUrl()).isEqualTo(VendorController.BASE_URL + "/1");
    }

    @Test
    public void createNewVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setName(NAME);
        savedVendor.setId(1L);

        Mockito.when(vendorRepository.save(Mockito.any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO savedDTO = vendorService.createNewVendor(vendorDTO);

        assertThat(savedDTO.getName()).isEqualTo(NAME);
        assertThat(savedDTO.getVendorUrl()).isEqualTo(VendorController.BASE_URL + "/1");
    }

    @Test
    public void saveVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setName(NAME);
        savedVendor.setId(1L);

        Mockito.when(vendorRepository.save(Mockito.any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO savedDTO = vendorService.saveVendor(1L, vendorDTO);

        assertThat(savedDTO.getName()).isEqualTo(NAME);
        assertThat(savedDTO.getVendorUrl()).isEqualTo(VendorController.BASE_URL + "/1");
    }

    @Test
    public void deleteVendor() {
        Long id = 1L;
        vendorService.deleteVendorById(id);

        Mockito.verify(vendorRepository, Mockito.times(1)).deleteById(id);
    }

}