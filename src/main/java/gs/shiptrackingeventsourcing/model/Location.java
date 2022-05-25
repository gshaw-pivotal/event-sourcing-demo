package gs.shiptrackingeventsourcing.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Data
@Jacksonized
public class Location {
    @NonNull
    public String shipId;

    @NonNull
    public String shipLocation;
}
