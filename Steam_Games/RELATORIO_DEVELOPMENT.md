# Relatório de Desenvolvimento - Sistema de Ordenação de Jogos Steam

## 1. Visão Geral do Projeto
Este projeto implementa um sistema de ordenação de jogos da Steam utilizando diversos algoritmos de ordenação. O sistema permite ordenar jogos por diferentes atributos como nome, preço, avaliações, data de lançamento, entre outros.

## 2. Desafios e Soluções Implementadas

### 2.1 Implementação do Quick Sort
**Problema Inicial:**
- A primeira implementação do Quick Sort utilizava apenas recursão, o que causava StackOverflowError com conjuntos de dados grandes
- O problema ocorria especialmente ao ordenar por atributos como "reviews" e "userScore" que tinham muitos valores repetidos

**Solução Implementada:**
- Implementação híbrida usando recursão e iteração
- Adição do Quick Sort com Mediana de 3 para melhorar o desempenho
- Implementação de um limite de profundidade de recursão
- Uso de insertion sort para subarrays pequenos (menos de 10 elementos)

### 2.2 Otimização do Counting Sort
**Problema Inicial:**
- O Counting Sort estava consumindo muita memória ao tentar ordenar todos os atributos
- Erro de OutOfMemoryError ao processar atributos com valores muito grandes

**Solução:**
- Restrição do Counting Sort apenas para atributos numéricos discretos (reviews e userScore)
- Implementação de um sistema de buckets para reduzir o uso de memória
- Adição de validação de dados para evitar valores negativos

### 2.3 Tratamento de Dados Inconsistentes
**Problemas Encontrados:**
- Valores nulos em campos críticos
- Formato inconsistente de datas
- Valores monetários com diferentes formatos de moeda

**Soluções:**
- Implementação de comparadores personalizados para cada tipo de dado
- Tratamento especial para valores nulos (colocados no final da ordenação)
- Normalização de datas para formato padrão
- Conversão de valores monetários para um formato unificado

### 2.4 Otimização de Memória
**Problema:**
- Uso excessivo de memória ao criar múltiplas cópias dos arrays para cada algoritmo

**Solução:**
- Implementação de um sistema de gerenciamento de memória
- Liberação explícita de arrays temporários
- Uso de System.gc() em pontos estratégicos após operações pesadas

## 3. Melhorias de Performance

### 3.1 Merge Sort
- Implementação de um buffer temporário reutilizável
- Otimização para arrays pequenos usando insertion sort
- Redução de alocações de memória

### 3.2 Heap Sort
- Otimização da construção do heap
- Implementação de um heap binário eficiente
- Redução de trocas desnecessárias

## 4. Testes e Validação

### 4.1 Casos de Teste
- Testes com conjuntos de dados de diferentes tamanhos
- Validação de ordenação com dados duplicados
- Testes de performance com diferentes distribuições de dados

### 4.2 Métricas de Performance
- Medição de tempo de execução para cada algoritmo
- Análise de uso de memória
- Comparação de eficiência entre diferentes implementações

## 5. Conclusões e Aprendizados

### 5.1 Principais Desafios
- Balanceamento entre performance e uso de memória
- Tratamento de casos especiais em diferentes algoritmos
- Otimização para diferentes tipos de dados

### 5.2 Melhorias Futuras
- Implementação de algoritmos de ordenação paralelos
- Otimização adicional para conjuntos de dados específicos
- Adição de mais métricas de performance

## 6. Notas Técnicas

### 6.1 Estrutura do Código
- Separação clara entre lógica de ordenação e gerenciamento de dados
- Uso de interfaces para facilitar extensibilidade
- Implementação de padrões de projeto para melhor manutenibilidade

### 6.2 Dependências
- Uso de bibliotecas padrão do Java
- Implementação de classes utilitárias personalizadas
- Gerenciamento eficiente de recursos do sistema

## 7. Ambiente de Execução

### 7.1 Configuração do Sistema
- Sistema Operacional: Windows 10 (versão 10.0.22631)
- Java Version: OpenJDK (versão utilizada no projeto)
- IDE: Eclipse (IDE utilizada no desenvolvimento)

### 7.2 Execução via Prompt de Comando
O projeto foi executado através do prompt de comando (CMD) do Windows, com os seguintes comandos:

```cmd
# Compilação
javac *.java

# Execução
java Main
```

### 7.3 Estrutura de Diretórios
- `/src`: Contém os arquivos fonte Java
- `/dados`: Contém os arquivos de entrada e saída
  - `/dados/games.csv`: Arquivo de entrada com os dados dos jogos
  - `/dados/resultados`: Diretório onde são salvos os resultados das ordenações

## 8. Casos de Teste Detalhados

### 8.1 Conjunto de Dados
O projeto utiliza um arquivo CSV contendo dados de jogos da Steam, com os seguintes atributos:
- name: Nome do jogo
- price: Preço do jogo
- reviews: Número de avaliações
- releaseDate: Data de lançamento
- developer: Desenvolvedora
- publisher: Publicadora
- metacriticScore: Pontuação no Metacritic
- userScore: Pontuação dos usuários
- approvalRate: Taxa de aprovação
- averagePlaytime: Tempo médio de jogo
- medianPlaytime: Tempo mediano de jogo

### 8.2 Processo de Teste
1. **Preparação dos Dados**
   - Leitura do arquivo CSV de entrada
   - Criação de cópias do array para cada algoritmo
   - Criação do diretório de resultados se não existir

2. **Execução dos Testes**
   - Para cada atributo:
     - Cópia do array original
     - Execução de cada algoritmo
     - Medição do tempo de execução
     - Salvamento dos resultados em CSV

3. **Geração de Relatórios**
   - Criação de arquivos CSV para cada combinação de algoritmo e atributo
   - Geração de relatório de tempos de execução no console

## 9. Análise Comparativa dos Algoritmos

### 9.1 Métricas de Performance
O sistema mede e registra o tempo de execução de cada algoritmo para cada atributo, gerando arquivos CSV separados com os resultados ordenados. Os tempos são medidos em milissegundos usando `System.currentTimeMillis()`.

### 9.2 Implementações Testadas
1. **Selection Sort**
   - Implementação básica do algoritmo
   - Testado em todos os atributos
   - Complexidade: O(n²)

2. **Insertion Sort**
   - Implementação básica do algoritmo
   - Testado em todos os atributos
   - Complexidade: O(n²)

3. **Merge Sort**
   - Implementação recursiva
   - Testado em todos os atributos
   - Complexidade: O(n log n)

4. **Quick Sort**
   - Implementação padrão
   - Testado em todos os atributos
   - Complexidade: O(n log n) médio, O(n²) pior caso

5. **Quick Sort com Mediana de 3**
   - Implementação otimizada
   - Testado em todos os atributos
   - Complexidade: O(n log n) médio, O(n²) pior caso

6. **Counting Sort**
   - Implementação específica para dados numéricos
   - Testado apenas em "reviews" e "userScore"
   - Complexidade: O(n + k), onde k é o range dos valores

7. **Heap Sort**
   - Implementação completa
   - Testado em todos os atributos
   - Complexidade: O(n log n)

### 9.3 Resultados
Os resultados completos dos testes podem ser encontrados nos arquivos CSV gerados na pasta `../dados/resultados/`, com o seguinte padrão de nomenclatura:
- `[algoritmo]_ordena_[atributo].csv`

Cada arquivo contém os dados ordenados pelo respectivo algoritmo e atributo, permitindo uma análise detalhada dos resultados.

### 9.4 Observações Importantes
- O Counting Sort foi implementado apenas para atributos numéricos discretos (reviews e userScore)
- Todos os algoritmos foram testados com o mesmo conjunto de dados para garantir uma comparação justa
- Os tempos de execução são medidos considerando apenas a operação de ordenação, excluindo o tempo de leitura/escrita dos arquivos
- O relatório de tempos é exibido no console em formato tabular para fácil comparação

---