package net.squareit.spring.kafka.helloworld.data.dao;

import net.squareit.spring.kafka.helloworld.data.entity.Span;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ISpanRepository extends MongoRepository<Span, Integer>{

    List<Span> findByTraceId(String traceId);

}
