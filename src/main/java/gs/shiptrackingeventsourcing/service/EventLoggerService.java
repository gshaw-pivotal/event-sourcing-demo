package gs.shiptrackingeventsourcing.service;

import gs.shiptrackingeventsourcing.model.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventLoggerService {

    private List<Event> eventList = new ArrayList();

    public void recordEvent(Event event) {
        eventList.add(event);
    }

    public List<Event> getEventLog() {
        return eventList;
    }
}
