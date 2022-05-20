package gs.shiptrackingeventsourcing;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ShipController {

    private Map<String, Ship> shipRegistry = new HashMap();

    @PostMapping(
            value = "/api/add",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity addShip(@RequestBody Ship ship) {
        shipRegistry.put(ship.id, ship);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/api/list")
    public ResponseEntity<List<Ship>> listShips() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new ArrayList(
                                shipRegistry.values()
                        )
                );
    }

    @DeleteMapping(value = "/api/remove/{id}")
    public ResponseEntity removeShip(@PathVariable String id) {
        shipRegistry.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
