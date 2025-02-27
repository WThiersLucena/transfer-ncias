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
}.

![image](https://github.com/user-attachments/assets/f65432d5-4fd9-4c92-8d8b-fc638ba28590)



2. O cálculo da taxa sobre o valor a ser transferido depende da data de transferência como 
segue:

![image](https://github.com/user-attachments/assets/82159cc9-baeb-4f9c-9280-ce8832477217)


3. O usuário deve poder ver o extrato de todos os agendamentos cadastrados.
   
Get -http://localhost:8080/transferencias/extrato.

![image](https://github.com/user-attachments/assets/28e815f0-aca4-40d3-a5bc-5432725c7335)

* Nova funcionabilidade disponibilizada no Back-End - Gerar PDF de extrato.
  
  Get - http://localhost:8080/transferencias/extrato/pdf.

![image](https://github.com/user-attachments/assets/78a424fb-4881-416f-8040-309d0a4a0940)


fazendo uso de dependencia itextpdf:

![image](https://github.com/user-attachments/assets/5b4b7a61-0cff-49b4-a719-4e701d986923)



* Nova funcionabilidade disponibilizada no Back-End - Gerar planilha Excle de extrato.
  
  Get - http://localhost:8080/transferencias/extrato/excel

  ![image](https://github.com/user-attachments/assets/0ebda14b-7911-4927-90ed-7b09bb084274)

  
* fazendo uso das seguintes dependencia :

![image](https://github.com/user-attachments/assets/8320e276-079e-4145-8dec-bca53e71036e)



Obs, nova funcionabilidade implementada somente nas classes Controller e Service, para exemplificação, o ideial e realizar conceitos de Solid, retirando sobrecarga de responsabilidades da função, ganhando mais desemprenho e melhor manutenciabilidade e seguindo padroes de boas praticas.
