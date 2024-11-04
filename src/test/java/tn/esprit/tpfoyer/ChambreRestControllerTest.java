package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tpfoyer.control.ChambreRestController;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.service.IChambreService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ChambreRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IChambreService chambreService;

    @InjectMocks
    private ChambreRestController chambreRestController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(chambreRestController).build();
    }

    @Test
    public void testGetChambres() throws Exception {
        List<Chambre> chambres = Arrays.asList(
                new Chambre(1, 101, TypeChambre.SIMPLE, null, null),
                new Chambre(2, 102, TypeChambre.DOUBLE, null, null)
        );
        when(chambreService.retrieveAllChambres()).thenReturn(chambres);

        mockMvc.perform(get("/chambre/retrieve-all-chambres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroChambre").value(101))
                .andExpect(jsonPath("$[1].numeroChambre").value(102));
    }

    @Test
    public void testRetrieveChambreById() throws Exception {
        Chambre chambre = new Chambre(1, 101, TypeChambre.SIMPLE, null, null);
        when(chambreService.retrieveChambre(1L)).thenReturn(chambre);

        mockMvc.perform(get("/chambre/retrieve-chambre/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroChambre").value(101));
    }

    @Test
    public void testAddChambre() throws Exception {
        Chambre chambre = new Chambre(1, 101, TypeChambre.SIMPLE, null, null);
        when(chambreService.addChambre(any(Chambre.class))).thenReturn(chambre);

        mockMvc.perform(post("/chambre/add-chambre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numeroChambre\": 101, \"typeC\": \"SIMPLE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroChambre").value(101));
    }

    @Test
    public void testRemoveChambre() throws Exception {
        doNothing().when(chambreService).removeChambre(1L);

        mockMvc.perform(delete("/chambre/remove-chambre/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testModifyChambre() throws Exception {
        Chambre chambre = new Chambre(1, 101, TypeChambre.SIMPLE, null, null);
        when(chambreService.modifyChambre(any(Chambre.class))).thenReturn(chambre);

        mockMvc.perform(put("/chambre/modify-chambre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idChambre\": 1, \"numeroChambre\": 101, \"typeC\": \"SIMPLE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroChambre").value(101));
    }
}

