package gs.shiptrackingeventsourcing.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder(toBuilder = true)
@Data
public class ShipRemoveEvent extends Event {

    @NonNull
    final EventType eventType = EventType.RemoveShip;

    @NonNull
    Ship ship;
}
