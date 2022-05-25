package gs.shiptrackingeventsourcing.service;

import gs.shiptrackingeventsourcing.model.Location;
import gs.shiptrackingeventsourcing.model.Ship;
import gs.shiptrackingeventsourcing.model.ShipLocationEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocationManagementService {

    private EventLoggerService eventLoggerService;

    private ShipManagementService shipManagementService;

    private Map<String, Location> locationRegistry = new HashMap();

    public LocationManagementService(
            EventLoggerService eventLoggerService,
            ShipManagementService shipManagementService
    ) {
        this.eventLoggerService = eventLoggerService;
        this.shipManagementService = shipManagementService;
    }

    public void updateShipLocation(Location location) {
        Ship shipToUpdate = shipManagementService.getShip(location.shipId);

        if (shipToUpdate != null) {
            locationRegistry.put(location.shipId, location);
            eventLoggerService.recordEvent(ShipLocationEvent.builder().ship(shipToUpdate).location(location.shipLocation).build());
        }
    }

    public List<Location> getCurrentLocations() {
        return new ArrayList(locationRegistry.values());
    }
}
