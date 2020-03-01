import Vue from 'vue'
import VueResource from 'vue-resource'
import App from 'pages/App.vue'
import Vuetify from "vuetify"
import 'vuetify/dist/vuetify.min.css'

Vue.use(VueResource)
Vue.use(Vuetify);

new Vue({
    el: '#app',
    render: a => a(App),
    vuetify: new Vuetify({}),
})