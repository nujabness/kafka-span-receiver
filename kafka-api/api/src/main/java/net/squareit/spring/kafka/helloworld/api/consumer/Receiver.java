package net.squareit.spring.kafka.helloworld.api.consumer;

import java.util.concurrent.CountDownLatch;

import com.google.gson.Gson;
import net.squareit.spring.kafka.helloworld.api.SendMessage;
import net.squareit.spring.kafka.helloworld.data.dao.IPrometheusRepository;
import net.squareit.spring.kafka.helloworld.data.dao.ISpanRepository;
import net.squareit.spring.kafka.helloworld.data.dao.ITraceRepository;
import net.squareit.spring.kafka.helloworld.data.entity.Prometheus;
import net.squareit.spring.kafka.helloworld.data.entity.Span;
import net.squareit.spring.kafka.helloworld.data.entity.Trace;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    SendMessage sendMessage;

    @Autowired
    private ISpanRepository spanRepository;

    @Autowired
    private ITraceRepository traceRepository;

    @Autowired
    private IPrometheusRepository prometheusRepository;

    @KafkaListener(topics = "jaeger-spans")
    public void receive(String payload) {
        LOGGER.info("received payload='{}'", payload);
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(payload);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(isTrace(json)){
            Span span = new Gson().fromJson(payload, Span.class);
            spanRepository.save(span);
        } else {
            Trace trace = new Gson().fromJson(payload, Trace.class);
            traceRepository.save(trace);
        }
        sendMessage.send("update");
    }

    private boolean isTrace(JSONObject json){
        if(json.get("references") != null) {
            return true;
        }
        return false;
    }


    @KafkaListener(topics = "metrics")
    public void receive2(String payload) {
        LOGGER.info("received payload='{}'", payload);
        Prometheus prometheus = new Prometheus();
        prometheus.setTrace(payload);
        prometheusRepository.save(prometheus);
    }
}
