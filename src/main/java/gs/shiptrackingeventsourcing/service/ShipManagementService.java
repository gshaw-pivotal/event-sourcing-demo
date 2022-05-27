package gs.shiptrackingeventsourcing.service;

import gs.shiptrackingeventsourcing.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShipManagementService {

    private EventLoggerService eventLoggerService;

    private Map<String, Ship> shipRegistry = new HashMap();

    private Map<String, Status> shipStatusRegistry = new HashMap();

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

    public void updateShipStatus(Status shipStatus) {
        Ship shipToStatusUpdate = shipRegistry.get(shipStatus.shipId);

        if (shipToStatusUpdate != null) {
            shipStatusRegistry.put(shipStatus.shipId, shipStatus);
            eventLoggerService.recordEvent(ShipStatusEvent.builder().ship(shipToStatusUpdate).shipStatus(shipStatus.shipStatus).build());
        }
    }

    public Ship getShip(String id) {
        return shipRegistry.get(id);
    }

    public Status getShipStatus(String id) { return shipStatusRegistry.get(id); }

    public List<Ship> getShipList() {
        return new ArrayList(shipRegistry.values());
    }

    public List<Status> getShipStatusList() { return new ArrayList(shipStatusRegistry.values()); }
}
