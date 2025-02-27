import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/', // Base URL do backend
    timeout: 5000, // Timeout para as requisições
    headers: {
      'Content-Type': 'application/json'
    }
  });
  
  // Função para enviar dados (POST)
  export const postData = async (endpoint, data) => {
    try {
      const response = await api.post(endpoint, data);
      return response.data;
    } catch (error) {
      console.error('Erro ao enviar dados:', error);
      throw error;
    }
  };