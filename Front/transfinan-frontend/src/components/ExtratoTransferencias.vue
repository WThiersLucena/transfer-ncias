<template>
  <div class="extrato-container">
    <h1 class="title">Extrato de Transferências</h1>
    <table class="extrato-table">
      <thead>
        <tr>
          <th>Conta Origem</th>
          <th>Conta Destino</th>
          <th>Valor</th>
          
          <th>Data Transferência</th>
          <th>Data Agendamento</th>
          <th>Taxa</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in extrato" :key="item.id">
          <td>{{ item.contaOrigem }}</td>
          <td>{{ item.contaDestino }}</td>
          <td>{{ formatCurrency(item.valor) }}</td> <!-- Adicionado R$ aqui -->          
          <td>{{ formatDate(item.dataTransferencia) }}</td>
          <td>{{ formatDate(item.dataAgendamento) }}</td>
          <td>{{ formatTaxa(item.taxa) }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'ExtratoTransferencias',
  data() {
    return {
      extrato: []  // Lista para armazenar os dados do extrato
    };
  },
  created() {
    this.carregarExtrato();
  },
  methods: {
    async carregarExtrato() {
      try {
        // Aqui você pode fazer a chamada à API para pegar os dados do extrato
        const response = await fetch('http://localhost:8080/transferencias/extrato');
        this.extrato = await response.json();
      } catch (error) {
        console.error('Erro ao carregar extrato', error);
      }
    },
    // Função para formatar os valores como moeda (R$)
    formatCurrency(value) {
      return `R$ ${value.toFixed(2).replace('.', ',')}`;
    },
    // Função para formatar a taxa com uma casa decimal
    formatTaxa(taxa) {
      return `${(Math.round(taxa * 10) / 100).toFixed(1).replace('.', ',')}%`;
    },
    // Função para formatar a data no formato DD/MM/AAAA
    formatDate(dateString) {
      const date = new Date(dateString);
      const day = String(date.getDate()).padStart(2, '0'); // Adiciona zero à esquerda se necessário
      const month = String(date.getMonth() + 1).padStart(2, '0'); // Meses começam de 0, por isso somamos 1
      const year = date.getFullYear();
      return `${day}/${month}/${year}`;
    }
  }
};
</script>

<style scoped>
.extrato-container {
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.title {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

.extrato-table {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
  background-color: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.extrato-table th,
.extrato-table td {
  padding: 12px;
  text-align: center;
  border-bottom: 1px solid #ddd;
  font-size: 14px;
  color: #333;
}

.extrato-table th {
  background-color: #4CAF50;
  color: white;
  font-weight: bold;
}

.extrato-table tr:nth-child(even) {
  background-color: #f2f2f2;
}

.extrato-table tr:hover {
  background-color: #f1f1f1;
  cursor: pointer;
}

.extrato-table td {
  font-size: 16px;
}

.extrato-table td:nth-child(3), .extrato-table td:nth-child(4) {
  font-weight: bold;
  color: #333;
}
</style>
