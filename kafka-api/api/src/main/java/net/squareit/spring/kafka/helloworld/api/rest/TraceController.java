package net.squareit.spring.kafka.helloworld.api.rest;

import net.squareit.spring.kafka.helloworld.data.dao.IPrometheusRepository;
import net.squareit.spring.kafka.helloworld.data.dao.ISpanRepository;
import net.squareit.spring.kafka.helloworld.data.dao.ITraceRepository;
import net.squareit.spring.kafka.helloworld.data.entity.Prometheus;
import net.squareit.spring.kafka.helloworld.data.entity.Span;
import net.squareit.spring.kafka.helloworld.data.entity.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/traces")
public class TraceController {

    @Autowired
    private ITraceRepository traceRepository;

    @Autowired
    private ISpanRepository spanRepository;

    @Autowired
    private IPrometheusRepository prometheusRepository;

    @GetMapping("/findAllTraces")
    public List<Trace> getTraces() {

        List<Trace> traces = traceRepository.findAll();
        traces.stream().forEach((item) -> {
            item.setServiceName(item.getProcess().getServiceName());
            List<Span> spans = spanRepository.findByTraceId(item.getTraceId());
            item.setSpans(spans);
        });

        return traces;
    }

    @GetMapping("/findAllPrometheus")
    public List<Prometheus> getAllPrometheus() {
        List<Prometheus> traces = prometheusRepository.findAll();
        return traces;
    }
}
