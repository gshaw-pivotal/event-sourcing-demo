package gs.shiptrackingeventsourcing.api;

import gs.shiptrackingeventsourcing.model.Event;
import gs.shiptrackingeventsourcing.service.EventLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventLoggerService eventLoggerService;

    @GetMapping(
            value = "/api/events",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Event>> getEventLog() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventLoggerService.getEventLog());
    }
}
