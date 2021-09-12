package net.squareit.spring.kafka.helloworld.data.dao;

import net.squareit.spring.kafka.helloworld.data.entity.Prometheus;
import net.squareit.spring.kafka.helloworld.data.entity.Trace;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IPrometheusRepository extends MongoRepository<Prometheus, Integer>{

}
