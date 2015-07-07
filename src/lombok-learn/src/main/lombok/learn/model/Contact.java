package learn.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@ToString
public class Contact {
    private int type;
    @NonNull
    private String value;
    private boolean primary;
}
