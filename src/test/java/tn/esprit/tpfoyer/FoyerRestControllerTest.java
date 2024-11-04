package tn.esprit.tpfoyer;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tpfoyer.control.FoyerRestController;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.service.IFoyerService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FoyerRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IFoyerService foyerService;

    @InjectMocks
    private FoyerRestController foyerRestController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(foyerRestController).build();
    }

    @Test
    public void testGetFoyers() throws Exception {
        List<Foyer> foyers = Arrays.asList(
                new Foyer(1L, "Foyer A", 100, null, null),
                new Foyer(2L, "Foyer B", 200, null, null)
        );
        when(foyerService.retrieveAllFoyers()).thenReturn(foyers);

        mockMvc.perform(get("/foyer/retrieve-all-foyers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomFoyer").value("Foyer A"))
                .andExpect(jsonPath("$[1].nomFoyer").value("Foyer B"));
    }

    @Test
    public void testRetrieveFoyer() throws Exception {
        Foyer foyer = new Foyer(1L, "Foyer A", 100, null, null);
        when(foyerService.retrieveFoyer(1L)).thenReturn(foyer);

        mockMvc.perform(get("/foyer/retrieve-foyer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomFoyer").value("Foyer A"));
    }

    @Test
    public void testAddFoyer() throws Exception {
        Foyer foyer = new Foyer(1L, "Foyer A", 100, null, null);
        when(foyerService.addFoyer(any(Foyer.class))).thenReturn(foyer);

        mockMvc.perform(post("/foyer/add-foyer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomFoyer\": \"Foyer A\", \"capaciteFoyer\": 100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomFoyer").value("Foyer A"));
    }

    @Test
    public void testRemoveFoyer() throws Exception {
        doNothing().when(foyerService).removeFoyer(1L);

        mockMvc.perform(delete("/foyer/remove-foyer/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testModifyFoyer() throws Exception {
        Foyer foyer = new Foyer(1L, "Foyer A", 100, null, null);
        when(foyerService.modifyFoyer(any(Foyer.class))).thenReturn(foyer);

        mockMvc.perform(put("/foyer/modify-foyer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idFoyer\": 1, \"nomFoyer\": \"Foyer A\", \"capaciteFoyer\": 100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomFoyer").value("Foyer A"));
    }
}
