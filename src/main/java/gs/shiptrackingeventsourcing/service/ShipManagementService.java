package gs.shiptrackingeventsourcing.service;

import gs.shiptrackingeventsourcing.model.Ship;
import gs.shiptrackingeventsourcing.model.ShipAddEvent;
import gs.shiptrackingeventsourcing.model.ShipRemoveEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShipManagementService {

    private EventLoggerService eventLoggerService;

    private Map<String, Ship> shipRegistry = new HashMap();

    public ShipManagementService(EventLoggerService eventLoggerService) {
        this.eventLoggerService = eventLoggerService;
    }

    public void addShip(Ship ship) {
        shipRegistry.put(ship.id, ship);
        eventLoggerService.recordEvent(ShipAddEvent.builder().ship(ship).build());
    }

    public void removeShip(String id) {
        Ship shipToRemove = shipRegistry.get(id);
        if (shipToRemove != null) {
            shipRegistry.remove(id);
            eventLoggerService.recordEvent(ShipRemoveEvent.builder().ship(shipToRemove).build());
        }
    }

    public Ship getShip(String id) {
        return shipRegistry.get(id);
    }

    public List<Ship> getShipList() {
        return new ArrayList(shipRegistry.values());
    }
}
