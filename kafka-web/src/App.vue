<template>
  <v-app>
    <v-content>
      <Traces/>
    </v-content>
  </v-app>
</template>

<script>
import Traces from './components/Traces';

export default {
  name: 'App',

  components: {Traces},

  data: () => ({

  }),
};
</script>
<style>
  @import "https://fonts.googleapis.com/css?family=Roboto|Montserrat:600|Open+Sans|Raleway:500";
</style>
