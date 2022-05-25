package gs.shiptrackingeventsourcing.api;

import gs.shiptrackingeventsourcing.model.Location;
import gs.shiptrackingeventsourcing.service.LocationManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController {

    @Autowired
    private LocationManagementService locationManagementService;

    @PostMapping(
            value = "/api/location/add",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity addLocation(@RequestBody Location location) {
        locationManagementService.updateShipLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/api/location/list")
    public ResponseEntity<List<Location>> listLocations() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        locationManagementService.getCurrentLocations()
                );
    }
}
