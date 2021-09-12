package net.squareit.spring.kafka.helloworld.data.dao;

import net.squareit.spring.kafka.helloworld.data.entity.Trace;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ITraceRepository extends MongoRepository<Trace, Integer>{

}
