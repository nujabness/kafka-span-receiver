package net.squareit.spring.kafka.helloworld.data.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "Prometheus")
public class Prometheus {

    private String trace;
}
