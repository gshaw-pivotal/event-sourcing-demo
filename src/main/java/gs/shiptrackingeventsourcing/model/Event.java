package gs.shiptrackingeventsourcing.model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class Event {

    ZonedDateTime eventTime;
}
