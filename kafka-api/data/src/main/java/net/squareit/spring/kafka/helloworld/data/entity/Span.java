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
@Document(collection = "Span")
public class Span {

    @Id
    private String spanId;
    private String traceId;
    private String operationName;
    private List<Reference> references;
    private int flags;
    private Date startTime;
    private String duration;
    private List<Tag> tags;
    private Process process;
}
