<template>
  <v-container>
    <v-row>
      <v-col cols="6">
        <h3>Jaeger Spans</h3>
        <v-expansion-panels>
          <v-expansion-panel
            v-for="(item) in payload"
            :key="item.spanId">
            <v-expansion-panel-header>
<!--              <span><strong>ServiceName:</strong> {{ item.serviceName }}</span>-->
              <span><strong>OperationName:</strong> {{ item.operationName }}</span>
              <span><strong>TraceId:</strong> {{ item.traceId }}</span>
<!--              <span><strong>SpanId:</strong> {{ item.spanId }}</span>-->
              <span><strong>Duration:</strong> {{ item.duration }}</span>
<!--              <span><strong>StartTime:</strong> {{ item.startTime }}</span>-->
            </v-expansion-panel-header>
            <v-expansion-panel-content>
              <Span v-for="(span) in item.spans" :key="span.spanId" :span="span"/>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-col>
      <v-col cols="6">
        <h3>Prometheus Spans</h3>
        <span v-for="metric in metrics" :key="metric">{{metric.trace.substr(0,100)}}</span>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  import SockJS from 'sockjs-client';
  import Stomp from 'webstomp-client';
  import {ALL_TRACES, ALL_PROMETHEUS} from '@/lib/request/RequestUri';
  import axios from 'axios';
  import Span from "./Span";

  export default {
    name: 'HelloWorld',
    components: {Span},
    data () {
     return {
       payload: [],
       metrics: []
     }
    },
    methods: {
      findAllSpans(){
        axios.get(ALL_TRACES).then( (success) => {
          this.payload = success.data
        }).catch((error) => {
          console.log(error);
        })
      },
      findAllPrometheus(){
        axios.get(ALL_PROMETHEUS).then( (success) => {
          this.metrics = success.data
        }).catch((error) => {
          console.log(error);
        })
      },
      send(message) {
        if (this.stompClient && this.stompClient.connected) {
          this.stompClient.send('/app-receive/from-client', message, {});
        }
      },
      connect() {
        this.socket = new SockJS("http://localhost:8090/websocket-endpoint");
        this.stompClient = Stomp.over(this.socket);
        this.stompClient.connect({}, () => {
          this.connected = true;
          this.stompClient.subscribe('/global-message/tick', (result) => {
            // eslint-disable-next-line
            if (result.body === 'update') {
              this.findAllSpans();
            }
          })
        }, (error) => {
          this.error.push(error);
          this.connected = false;
        })
      },
      disconnectWS() {
        if (this.stompClient) {
          this.stompClient.disconnect();
        }
        this.connected = false;
      },
      tickleConnection() {
        this.connected ? this.disconnectWS() : this.connect();
      },
    },

    mounted() {
      axios.defaults.baseURL = 'http://localhost:8090/';
      this.connect();
      this.findAllSpans();
      this.findAllPrometheus();
    }
  }
</script>
