package gs.shiptrackingeventsourcing.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder(toBuilder = true)
@Data
public class ShipLocationEvent extends Event {

    @NonNull
    final EventType eventType = EventType.Location;

    @NonNull
    Ship ship;

    @NonNull
    String location;
}
