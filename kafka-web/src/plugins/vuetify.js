import Vue from 'vue';
import Vuetify from 'vuetify/lib';

Vue.use(Vuetify);

export default new Vuetify({
    icons: {
        iconfont: 'mdi',
    },
    theme: {
        themes: {
            light: {
                primary: '#FC9880',
                secondary: '#D86A50',
                light_peach: '#FFD8CE',
                dark_peach: '#B6462C',
                smooth: '#FFB8A8',
                white: '#FFFFFF',
                black: '#000000'
            }
        }
    }
});
