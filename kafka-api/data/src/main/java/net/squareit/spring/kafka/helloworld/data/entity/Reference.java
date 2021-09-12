package net.squareit.spring.kafka.helloworld.data.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Reference {

    private String traceId;
    private String spanId;
}
