import Vue from 'vue';
import Router from 'vue-router';
import AgendarTarefa from './components/AgendarTarefa.vue'
import ExtratoTransferencias from './components/ExtratoTransferencias.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: AgendarTarefa
    },
    {
      path: '/extratotransferencias', 
      name: 'extratotransferencias',
      component: ExtratoTransferencias
    }
  ]
});
