Tecnologias Utilizadas

Spring Boot: Framework para desenvolvimento da aplicação.

RestTemplate: Cliente HTTP para realizar as requisições.

JPA (Spring Data): Para gravação de logs de consulta no banco de dados.

H2 : Banco de dados para armazenamento.

Mock: para testes unitarios  

Conceitos de SOLID e Microserviços 

Front: VueJs

O usuário deve poder agendar uma transferência financeira com as seguintes informações: 

1. Conta de origem (padrão XXXXXXXXXX), conta de destino (padrão XXXXXXXXXX), valor da 
transferência, taxa (a ser calculada conforme tabela abaixo), data da transferência (data em 
que será realizada a transferência) e data de agendamento (hoje);
Post http://localhost:8080/transferencias/agendar
{
    "contaOrigem": "1234567890",
    "contaDestino": "0987654321",
    "valor": 1000.00,
    "dataTransferencia": "2025-03-20" 
}
![image](https://github.com/user-attachments/assets/f65432d5-4fd9-4c92-8d8b-fc638ba28590)



2. O cálculo da taxa sobre o valor a ser transferido depende da data de transferência como 
segue:

![image](https://github.com/user-attachments/assets/82159cc9-baeb-4f9c-9280-ce8832477217)


3. O usuário deve poder ver o extrato de todos os agendamentos cadastrados. 
Get -http://localhost:8080/transferencias/extrato

![image](https://github.com/user-attachments/assets/28e815f0-aca4-40d3-a5bc-5432725c7335)

