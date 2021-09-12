package net.squareit.spring.kafka.helloworld.data.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "Trace")
public class Trace {

    @Id
    private String spanId;
    private String duration;
    private int flags;
    private String serviceName;
    private String operationName;
    private Process process;
    private Date startTime;
    private List<Tag> tags;
    private String traceId;
    private List<Span> spans;
}
