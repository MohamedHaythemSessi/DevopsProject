package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.service.IUniversiteService;
import tn.esprit.tpfoyer.control.UniversiteRestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UniversiteRestControllerTest {

    @Mock
    private IUniversiteService universiteService;

    @InjectMocks
    private UniversiteRestController universiteRestController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testGetUniversites() {
        // Arrange
        List<Universite> universites = new ArrayList<>();
        universites.add(new Universite(1L, "Universite A", "Address A", null));
        universites.add(new Universite(2L, "Universite B", "Address B", null));

        when(universiteService.retrieveAllUniversites()).thenReturn(universites);

        // Act
        List<Universite> result = universiteRestController.getUniversites();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Universite A", result.get(0).getNomUniversite());
        verify(universiteService, times(1)).retrieveAllUniversites();
    }

    @Test
    public void testRetrieveUniversite() {
        // Arrange
        Long universiteId = 1L;
        Universite universite = new Universite(universiteId, "Universite A", "Address A", null);

        when(universiteService.retrieveUniversite(universiteId)).thenReturn(universite);

        // Act
        Universite result = universiteRestController.retrieveUniversite(universiteId);

        // Assert
        assertNotNull(result);
        assertEquals("Universite A", result.getNomUniversite());
        assertEquals("Address A", result.getAdresse());
        verify(universiteService, times(1)).retrieveUniversite(universiteId);
    }

    @Test
    public void testAddUniversite() {
        // Arrange
        Universite universite = new Universite(0L, "Universite C", "Address C", null);
        Universite savedUniversite = new Universite(1L, "Universite C", "Address C", null);

        when(universiteService.addUniversite(any(Universite.class))).thenReturn(savedUniversite);

        // Act
        Universite result = universiteRestController.addUniversite(universite);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdUniversite());
        assertEquals("Universite C", result.getNomUniversite());
        verify(universiteService, times(1)).addUniversite(universite);
    }

    @Test
    public void testRemoveUniversite() {
        // Arrange
        Long universiteId = 1L;

        // Act
        universiteRestController.removeUniversite(universiteId);

        // Assert
        verify(universiteService, times(1)).removeUniversite(universiteId);
    }

    @Test
    public void testModifyUniversite() {
        // Arrange
        Universite universite = new Universite(1L, "Universite D", "Address D", null);

        when(universiteService.modifyUniversite(any(Universite.class))).thenReturn(universite);

        // Act
        Universite result = universiteRestController.modifyUniversite(universite);

        // Assert
        assertNotNull(result);
        assertEquals("Universite D", result.getNomUniversite());
        verify(universiteService, times(1)).modifyUniversite(universite);
    }
}
