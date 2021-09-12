package net.squareit.spring.kafka.helloworld.data.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Process {

    private String serviceName;
    private List<Tag> tags;
}
