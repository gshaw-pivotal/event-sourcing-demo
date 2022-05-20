package gs.shiptrackingeventsourcing;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Data
@Jacksonized
public class Ship {
    @NonNull
    String id;

    @NonNull
    String name;
}
