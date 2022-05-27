package gs.shiptrackingeventsourcing.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder(toBuilder = true)
@Data
public class ShipStatusEvent extends Event{
    @NonNull
    final EventType eventType = EventType.Status;

    @NonNull
    Ship ship;

    @NonNull
    StatusType shipStatus;
}
