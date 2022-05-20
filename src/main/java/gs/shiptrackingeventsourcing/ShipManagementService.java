package gs.shiptrackingeventsourcing;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShipManagementService {

    private Map<String, Ship> shipRegistry = new HashMap();

    public void addShip(Ship ship) {
        shipRegistry.put(ship.id, ship);
    }

    public void removeShip(String id) {
        shipRegistry.remove(id);
    }

    public List<Ship> getShipList() {
        return new ArrayList(shipRegistry.values());
    }
}
