package us.vicentini.sfgjms.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HelloWorldMessage implements Serializable {
    private static final long serialVersionUID = -8992031456518113857L;

    private UUID id;
    private String message;
}
