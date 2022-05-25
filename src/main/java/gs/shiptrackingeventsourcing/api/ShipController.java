package gs.shiptrackingeventsourcing.api;

import gs.shiptrackingeventsourcing.model.Ship;
import gs.shiptrackingeventsourcing.service.ShipManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShipController {

    @Autowired
    private ShipManagementService shipManagementService;

    @PostMapping(
            value = "/api/ship/add",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity addShip(@RequestBody Ship ship) {
        shipManagementService.addShip(ship);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/api/ship/list")
    public ResponseEntity<List<Ship>> listShips() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        shipManagementService.getShipList()
                );
    }

    @GetMapping(value = "/api/ship/{id}")
    public ResponseEntity<Ship> getShip(@PathVariable String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        shipManagementService.getShip(id)
                );
    }

    @DeleteMapping(value = "/api/ship/remove/{id}")
    public ResponseEntity removeShip(@PathVariable String id) {
        shipManagementService.removeShip(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
