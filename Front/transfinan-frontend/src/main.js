import { createApp } from "vue";
import App from "./App.vue";
import { createRouter, createWebHistory } from "vue-router";

import AgendarTransferencia from "./components/AgendarTransferencia.vue"; // Certifique-se de que o caminho está correto
import ExtratoTransferencias from "./components/ExtratoTransferencias.vue";

// Criando o Vue Router com a configuração das rotas
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes: [
    {
      path: "/", // Defina o caminho que você deseja para a tela
      name: "AgendarTransferencia",
      component: AgendarTransferencia,
    },
    {
      path: "/extratoTransferencias", // Rota para ExibirTela2
      name: "extratoTransferencias",
      component: ExtratoTransferencias,
    },
  ],
});

// Criando a aplicação Vue e configurando o router
createApp(App)
  .use(router) // Usando o Vue Router na aplicação
  .mount("#app");
