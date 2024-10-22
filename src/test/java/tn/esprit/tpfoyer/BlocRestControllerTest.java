package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.service.IBlocService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BlocRestControllerTest { // Changed class name to avoid conflict with controller

    @Mock
    private IBlocService blocService;

    @InjectMocks
    private tn.esprit.tpfoyer.control.BlocRestController blocRestController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testGetBlocs() {
        // Arrange
        List<Bloc> blocs = new ArrayList<>();
        blocs.add(new Bloc(1L, "Bloc A", 100L, null, new HashSet<>()));
        blocs.add(new Bloc(2L, "Bloc B", 200L, null, new HashSet<>()));

        when(blocService.retrieveAllBlocs()).thenReturn(blocs);

        // Act
        List<Bloc> result = blocRestController.getBlocs();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Bloc A", result.get(0).getNomBloc());
    }

    @Test
    public void testRetrieveBloc() {
        // Arrange
        Long blocId = 1L;
        Bloc bloc = new Bloc(blocId, "Bloc A", 100L, null, new HashSet<>());

        // Mock the service method
        when(blocService.retrieveBloc(blocId)).thenReturn(bloc);

        // Act
        Bloc result = blocRestController.retrieveBloc(blocId);

        // Assert
        assertNotNull(result);
        assertEquals(bloc.getIdBloc(),result.getIdBloc());
        assertEquals(bloc.getNomBloc(),result.getNomBloc());

    }

    @Test
    public void testAddBloc() {
        // Arrange
        Bloc bloc = new Bloc(0L, "Bloc A", 100L, null, new HashSet<>()); // Use proper constructor

        when(blocService.addBloc(any(Bloc.class))).thenReturn(new Bloc(1L, "Bloc A", 100L, null, new HashSet<>()));

        // Act
        Bloc result = blocRestController.addBloc(bloc);

        // Assert
        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
    }

    @Test
    public void testRemoveBloc() {
        // Arrange
        Long blocId = 1L;

        // Act
        blocRestController.removeBloc(blocId);

        // Assert
        verify(blocService, times(1)).removeBloc(blocId);
    }

    @Test
    public void testModifyBloc() {
        // Arrange
        Bloc bloc = new Bloc(1L, "Bloc A", 100L, null, new HashSet<>()); // Pass a valid id

        when(blocService.modifyBloc(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc result = blocRestController.modifyBloc(bloc);

        // Assert
        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
    }

    @Test
    public void testGetBlocswithoutFoyer() {
        // Arrange
        List<Bloc> blocsWithoutFoyer = new ArrayList<>();
        blocsWithoutFoyer.add(new Bloc(1L, "Bloc A", 100L, null, new HashSet<>()));

        when(blocService.trouverBlocsSansFoyer()).thenReturn(blocsWithoutFoyer);

        // Act
        List<Bloc> result = blocRestController.getBlocswirhoutFoyer();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Bloc A", result.get(0).getNomBloc());
    }
}
