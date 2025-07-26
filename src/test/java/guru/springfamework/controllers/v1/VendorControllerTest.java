package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.services.ResourceNotFoundException;
import guru.springfamework.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {VendorController.class})
class VendorControllerTest extends AbstractRestControllerTest {

    private static final String NAME = "Test";

    @MockBean
    VendorService vendorService;

    @Autowired
    MockMvc mockMvc;

    VendorDTO vendor1;
    VendorDTO vendor2;

    @BeforeEach
    public void setUp() {
        vendor1 = new VendorDTO();
        vendor1.setName(NAME);
        vendor1.setVendorUrl(VendorController.BASE_URL + "/1");

        vendor2 = new VendorDTO();
        vendor2.setName("Vendor 2");
        vendor2.setVendorUrl(VendorController.BASE_URL + "/2");
    }

    @Test
    public void testListVendors() throws Exception {
        given(vendorService.getAllVendors()).willReturn(List.of(vendor1, vendor2));

        mockMvc.perform(get(VendorController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void testGetVendorById() throws Exception {
        given(vendorService.getVendorById(1L)).willReturn(vendor1);

        mockMvc.perform(get(VendorController.BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo(NAME)))
            .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    public void testCreateNewVendor() throws Exception {
        VendorDTO returnVendor = new VendorDTO();
        returnVendor.setName(NAME);
        returnVendor.setVendorUrl(VendorController.BASE_URL + "/1");

        given(vendorService.createNewVendor(Mockito.any(VendorDTO.class))).willReturn(returnVendor);

        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor1)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", equalTo(NAME)))
            .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    public void testUpdateVendor() throws Exception {
        VendorDTO returnVendor = new VendorDTO();
        returnVendor.setName(NAME);
        returnVendor.setVendorUrl(VendorController.BASE_URL + "/1");

        given(vendorService.saveVendor(Mockito.eq(1L), Mockito.any(VendorDTO.class))).willReturn(returnVendor);

        mockMvc.perform(put(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor1)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo(NAME)))
            .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    public void testPatchVendor() throws Exception {
        VendorDTO returnVendor = new VendorDTO();
        returnVendor.setName(NAME);
        returnVendor.setVendorUrl(VendorController.BASE_URL + "/1");

        given(vendorService.patchVendor(Mockito.eq(1L), Mockito.any(VendorDTO.class))).willReturn(returnVendor);

        mockMvc.perform(patch(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor1)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo(NAME)))
            .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }


    @Test
    public void testDeleteVendor() throws Exception {

        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        then(vendorService).should().deleteVendorById(1L);
    }

    @Test
    public void testGetVendorByIdNotFound() throws Exception {

        given(vendorService.getVendorById(1L)).willThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}