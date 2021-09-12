#!/bin/bash

docker stop jaeger-collector prometheus-kafka-adapter prometheus helloworld
docker rm jaeger-collector prometheus-kafka-adapter prometheus helloworld

docker network create -d bridge monitoring-net
docker run -d --network monitoring-net --rm --name jaeger-collector -e SPAN_STORAGE_TYPE=kafka -e KAFKA_PRODUCER_BROKERS=192.168.1.29:9092 -e KAFKA_PRODUCER_ENCODING=json jaegertracing/jaeger-collector:latest --log-level=debug
docker run -d --network monitoring-net --rm --name prometheus-kafka-adapter -e KAFKA_BROKER_LIST=192.168.1.29:9092 telefonica/prometheus-kafka-adapter:1.6.0
docker run -d --network monitoring-net --rm --name prometheus -p 9090:9090 -v C:\Users\melassouri\Documents\Project\tibco\prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml
docker run -d --rm --name helloworld -p 7575:8080 -p 9095:9095 –e BW_PROMETHEUS_ENABLE=true –e BW_JAVA_OPTS=”-Dbw.engine.opentracing.enable=true” –e JAEGER_ENDPOINT=http://jaeger-collector:14268/api/traces -e JAEGER_SAMPLER_TYPE=const -e JAEGER_SAMPLER_PARAM=1 bwce/helloworld:latest
docker network connect monitoring-net helloworld