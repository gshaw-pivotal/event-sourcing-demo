package gs.shiptrackingeventsourcing.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder(toBuilder = true)
@Data
public class ShipAddEvent extends Event {

    @NonNull
    final EventType eventType = EventType.AddShip;

    @NonNull
    Ship ship;
}
