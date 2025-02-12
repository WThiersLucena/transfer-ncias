<template>
  <div class="agendar-transferencia">
    <h1>Agendar Transferência</h1>
    <form @submit.prevent="agendarTransferencia" class="form-container">
      <div class="input-group">
        <label for="contaOrigem">Conta de Origem</label>
        <input
          type="text"
          id="contaOrigem"
          v-model="transferencia.contaOrigem"
          required
        />
      </div>

      <div class="input-group">
        <label for="contaDestino">Conta de Destino</label>
        <input
          type="text"
          id="contaDestino"
          v-model="transferencia.contaDestino"
          required
        />
      </div>

      <div class="input-group">
        <label for="valor">Valor</label>
        <input
          type="number"
          id="valor"
          v-model="transferencia.valor"
          required
        />
      </div>

      <div class="input-group">
        <label for="dataTransferencia">Data da Transferência</label>
        <input
          type="date"
          id="dataTransferencia"
          v-model="transferencia.dataTransferencia"
          required
        />
      </div>

      <button type="submit" class="btn-submit">Agendar Transferência</button>
    </form>

    <!-- Mensagens de Sucesso ou Erro -->
    <p v-if="sucesso" class="msg-sucesso">
      Transferência agendada com sucesso!
    </p>
    <p v-if="erro" class="msg-erro">Erro ao agendar a transferência.</p>

    <!-- Botão de Extrato -->
    <button @click="verExtrato" v-if="showExtratoButton" class="btn-extrato">
      Extrato
    </button>
  </div>
</template>

<script>
import { postData } from "../services/apiService"; // Método de envio para o backend

export default {
  name: "AgendarTransferencia",
  data() {
    return {
      transferencia: {
        contaOrigem: "",
        contaDestino: "",
        valor: "",
        dataTransferencia: "",
      },
      sucesso: false,
      erro: false,
      showExtratoButton: false, // Controla a exibição do botão "Extrato"
    };
  },
  methods: {
    // Método para agendar a transferência
    async agendarTransferencia() {
      try {
        // Dados da transferência
        const transferenciaData = {
          contaOrigem: this.transferencia.contaOrigem,
          contaDestino: this.transferencia.contaDestino,
          valor: parseFloat(this.transferencia.valor),
          dataTransferencia: this.transferencia.dataTransferencia,
        };

        // Enviar dados para o backend
        await postData("/transferencias/agendar", transferenciaData);

        // Exibir mensagem de sucesso
        this.sucesso = true;
        this.erro = false;

        // Limpar campos após o envio
        this.limparCampos();

        // Esconder mensagem de sucesso e exibir botão "Extrato" após 2 segundos
        setTimeout(() => {
          this.sucesso = false;
          this.showExtratoButton = true;
        }, 2000);
      } catch (error) {
        this.erro = true;
        this.sucesso = false;
        console.error("Erro ao agendar a transferência:", error);
      }
    },

    // Método para limpar os campos do formulário
    limparCampos() {
      this.transferencia.contaOrigem = "";
      this.transferencia.contaDestino = "";
      this.transferencia.valor = "";
      this.transferencia.dataTransferencia = "";
    },

    // Método para exibir o extrato
    verExtrato() {
      this.$router.push("/ExtratoTransferencias"); // Navega para a tela de extrato
    },
  },
};
</script>

<style scoped>
.agendar-transferencia {
  max-width: 400px;
  margin: 0 auto;
}

.form-container {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.input-group {
  display: flex;
  flex-direction: column;
}

label {
  font-size: 14px;
  margin-bottom: 5px;
}

input {
  padding: 8px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 12px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

button:hover {
  background-color: #45a049;
}

.msg-sucesso {
  color: green;
  text-align: center;
}

.msg-erro {
  color: red;
  text-align: center;
}

.btn-extrato {
  background-color: #28a745;
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  font-size: 16px;
  border-radius: 4px;
  margin-top: 20px;
}

.btn-extrato:hover {
  background-color: #218838;
}
</style>
