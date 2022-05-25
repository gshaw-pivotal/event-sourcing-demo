package gs.shiptrackingeventsourcing.service;

import gs.shiptrackingeventsourcing.model.*;
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
import static org.mockito.Mockito.times;

class LocationManagementServiceTest {

    @Mock
    private EventLoggerService eventLoggerService;

    @Mock
    private ShipManagementService shipManagementService;

    @InjectMocks
    private LocationManagementService locationManagementService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        locationManagementService = new LocationManagementService(eventLoggerService, shipManagementService);
    }

    @Test
    public void updateShipLocation_givenAnIdForAShipThatExistsRecordTheLocation() {
        List<Event> expectedEvents = asList(
                ShipLocationEvent.builder().ship(Ship.builder().id("FB").name("Foobar").build()).location("new location").build()
        );

        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);

        when(shipManagementService.getShip("FB")).thenReturn(Ship.builder().id("FB").name("Foobar").build());
        doNothing().when(eventLoggerService).recordEvent(argumentCaptor.capture());

        locationManagementService.updateShipLocation(Location.builder().shipId("FB").shipLocation("new location").build());

        Assertions.assertTrue(locationManagementService.getCurrentLocations().size() == 1);
        Assertions.assertTrue(locationManagementService.getCurrentLocations().containsAll(List.of(
                Location.builder().shipId("FB").shipLocation("new location").build()
        )));

        verify(eventLoggerService, times(1)).recordEvent(any());

        List<Event> events = argumentCaptor.getAllValues();
        Assertions.assertTrue(events.containsAll(expectedEvents));
    }

    @Test
    public void updateShipLocation_givenAnIdForAShipThatDoesNotExistDoesNotRecordTheLocation() {
        when(shipManagementService.getShip("FB")).thenReturn(null);

        locationManagementService.updateShipLocation(Location.builder().shipId("FB").shipLocation("new location").build());

        Assertions.assertTrue(locationManagementService.getCurrentLocations().size() == 0);

        verifyNoInteractions(eventLoggerService);
    }
}