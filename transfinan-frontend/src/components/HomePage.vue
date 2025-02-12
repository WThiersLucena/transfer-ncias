<template>
  <div>
    <h1>Bem-vindo ao Transfinan</h1>
    <router-link to="/agendar">Agendar Transferência</router-link>
  </div>
</template>


<script>
// Importando os métodos do serviço
import { getData, postData } from '../services/apiService';

export default {
  name: 'HomePage',
  data() {
    return {
      data: null, // Para armazenar os dados que virão da API
    };
  },
  methods: {
    async fetchData() {
      try {
        this.data = await getData(); // Chamando o serviço para pegar os dados
      } catch (error) {
        console.error('Erro ao buscar dados', error);
      }
    },
    async sendData() {
      const dataToSend = { nome: 'Novo Dado', valor: 123 };

      try {
        const response = await postData(dataToSend); // Enviando dados para a API
        console.log('Dados enviados:', response);
      } catch (error) {
        console.error('Erro ao enviar dados', error);
      }
    }
  },
  created() {
    this.fetchData(); // Chama o fetchData quando o componente é criado
  }
};
</script>

<style scoped>
/* Estilos do componente */
</style>
