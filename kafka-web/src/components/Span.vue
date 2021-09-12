<template>
  <v-row>
      <v-col>
          <span><strong>SpanId:</strong> {{ span.spanId }}</span>
      </v-col>
      <v-col>
          <span><strong>Duration:</strong> {{ span.duration }}</span>
      </v-col>
      <v-col>
          <span><strong>StartTime:</strong> {{ span.startTime }}</span>
      </v-col>
  </v-row>
</template>

<script>
  export default {
      props:['span'],
      name: 'Span',
    data () {

    },
  }
</script>
