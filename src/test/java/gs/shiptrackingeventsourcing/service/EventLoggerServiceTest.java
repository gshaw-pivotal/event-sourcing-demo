package gs.shiptrackingeventsourcing.service;

import gs.shiptrackingeventsourcing.model.Event;
import gs.shiptrackingeventsourcing.model.Ship;
import gs.shiptrackingeventsourcing.model.ShipAddEvent;
import gs.shiptrackingeventsourcing.service.EventLoggerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class EventLoggerServiceTest {

    private EventLoggerService eventLoggerService;

    @BeforeEach
    public void setup() {
        eventLoggerService = new EventLoggerService();
    }

    @Test
    public void recordEvent_addsTheEventToTheLog() {
        Event event = ShipAddEvent.builder().ship(Ship.builder().id("FB").name("Foobar").build()).build();
        eventLoggerService.recordEvent(event);

        Assertions.assertTrue(eventLoggerService.getEventLog().containsAll(List.of(event)));
    }
}