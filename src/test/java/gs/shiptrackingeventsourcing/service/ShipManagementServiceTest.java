package gs.shiptrackingeventsourcing.service;

import gs.shiptrackingeventsourcing.model.Event;
import gs.shiptrackingeventsourcing.model.Ship;
import gs.shiptrackingeventsourcing.model.ShipAddEvent;
import gs.shiptrackingeventsourcing.model.ShipRemoveEvent;
import gs.shiptrackingeventsourcing.service.EventLoggerService;
import gs.shiptrackingeventsourcing.service.ShipManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ShipManagementServiceTest {

    @Mock
    private EventLoggerService eventLoggerService;

    @InjectMocks
    private ShipManagementService shipManagementService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        shipManagementService = new ShipManagementService(eventLoggerService);
    }

    @Test
    public void addShip_addsNewShipsToRegistry() {
        List<Event> expectedEvents = asList(
                ShipAddEvent.builder().ship(Ship.builder().id("FB").name("Foobar").build()).build(),
                ShipAddEvent.builder().ship(Ship.builder().id("BF").name("Barfoo").build()).build()
        );
        List<Ship> expectedShips = asList(
                Ship.builder().id("FB").name("Foobar").build(),
                Ship.builder().id("BF").name("Barfoo").build()
        );

        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        doNothing().when(eventLoggerService).recordEvent(argumentCaptor.capture());

        Assertions.assertEquals(0, shipManagementService.getShipList().size());

        shipManagementService.addShip(Ship.builder().id("FB").name("Foobar").build());
        shipManagementService.addShip(Ship.builder().id("BF").name("Barfoo").build());

        Assertions.assertEquals(2, shipManagementService.getShipList().size());
        Assertions.assertTrue(shipManagementService.getShipList().containsAll(expectedShips));

        verify(eventLoggerService, times(2)).recordEvent(any());

        List<Event> events = argumentCaptor.getAllValues();
        Assertions.assertTrue(events.containsAll(expectedEvents));
    }

    @Test
    public void addShip_doesNotAddDuplicateShipsToRegistry() {
        doNothing().when(eventLoggerService).recordEvent(any());

        Assertions.assertEquals(0, shipManagementService.getShipList().size());

        shipManagementService.addShip(Ship.builder().id("FB").name("Foobar").build());
        shipManagementService.addShip(Ship.builder().id("FB").name("Foobar").build());

        Assertions.assertEquals(1, shipManagementService.getShipList().size());
    }

    @Test
    public void removeShip_removesShipThatExistsFromRegistry() {
        List<Event> expectedEvents = asList(
                ShipAddEvent.builder().ship(Ship.builder().id("FB").name("Foobar").build()).build(),
                ShipAddEvent.builder().ship(Ship.builder().id("BF").name("Barfoo").build()).build(),
                ShipRemoveEvent.builder().ship(Ship.builder().id("BF").name("Barfoo").build()).build()
        );
        List<Ship> expectedShips = asList(
                Ship.builder().id("FB").name("Foobar").build()
        );

        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        doNothing().when(eventLoggerService).recordEvent(argumentCaptor.capture());

        Assertions.assertEquals(0, shipManagementService.getShipList().size());

        shipManagementService.addShip(Ship.builder().id("FB").name("Foobar").build());
        shipManagementService.addShip(Ship.builder().id("BF").name("Barfoo").build());

        Assertions.assertEquals(2, shipManagementService.getShipList().size());

        shipManagementService.removeShip("BF");

        Assertions.assertEquals(1, shipManagementService.getShipList().size());
        Assertions.assertTrue(shipManagementService.getShipList().containsAll(expectedShips));

        verify(eventLoggerService, times(3)).recordEvent(any());

        List<Event> events = argumentCaptor.getAllValues();
        Assertions.assertTrue(events.containsAll(expectedEvents));
    }

    @Test
    public void removeShip_doesNotRemovesShipThatDoesNotExistsFromRegistry() {
        doNothing().when(eventLoggerService).recordEvent(any());

        Assertions.assertEquals(0, shipManagementService.getShipList().size());

        shipManagementService.addShip(Ship.builder().id("FB").name("Foobar").build());
        shipManagementService.addShip(Ship.builder().id("BF").name("Barfoo").build());

        Assertions.assertEquals(2, shipManagementService.getShipList().size());

        shipManagementService.removeShip("AA");

        Assertions.assertEquals(2, shipManagementService.getShipList().size());
    }

    @Test
    public void getShipList_returnsTheCompleteCurrentRegistry() {
        doNothing().when(eventLoggerService).recordEvent(any());

        shipManagementService.addShip(Ship.builder().id("FB").name("Foobar").build());
        shipManagementService.addShip(Ship.builder().id("BF").name("Barfoo").build());

        List<Ship> expectedList = asList(
                Ship.builder().id("FB").name("Foobar").build(),
                Ship.builder().id("BF").name("Barfoo").build()
        );

        Assertions.assertTrue(shipManagementService.getShipList().containsAll(expectedList));

        shipManagementService.removeShip("AA");

        Assertions.assertTrue(shipManagementService.getShipList().containsAll(expectedList));

        shipManagementService.removeShip("BF");

        Assertions.assertTrue(shipManagementService.getShipList().containsAll(List.of(
                Ship.builder().id("FB").name("Foobar").build()
        )));
    }
}