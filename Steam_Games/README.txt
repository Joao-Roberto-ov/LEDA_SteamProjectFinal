# Análise de Jogos da Steam

Este projeto realiza uma análise completa dos jogos da Steam, incluindo conversão de datas, filtragem por suporte a Linux e português, e ordenação usando diferentes algoritmos.

## Funcionalidades

1. **Conversão de Datas**
   - Converte as datas do formato original para o formato brasileiro (dd/mm/yyyy)
   - Arquivo de saída: `games_formated_release_data.csv`

2. **Filtragem de Jogos**
   - Filtra jogos com suporte a Linux
   - Filtra jogos com suporte a português
   - Arquivos de saída: `games_linux.csv` e `games_portugues.csv`

3. **Ordenação de Jogos**
   - Ordena jogos por diferentes atributos:
     - Data de lançamento
     - Preço
     - Número de achievements
   - Implementa 7 algoritmos de ordenação:
     - Selection Sort
     - Insertion Sort
     - Merge Sort
     - Quick Sort
     - Quick Sort com Mediana de 3
     - Heap Sort
     - Counting Sort (apenas para achievements)
   - Testa cada algoritmo em três cenários:
     - Melhor caso
     - Caso médio
     - Pior caso
   - Mede o tempo de execução e uso de memória para cada algoritmo
   - Gera uma tabela formatada com os resultados
   - Salva os resultados ordenados em arquivos CSV

## Estrutura de Arquivos

```
Steam_Games/
├── src/
│   ├── Main.java                 # Classe principal
│   ├── Game.java                 # Classe que representa um jogo
│   ├── CSVManager.java           # Gerenciamento de arquivos CSV
│   ├── ConversorData.java        # Conversão de datas
│   ├── FiltroLinux.java          # Filtro para jogos Linux
│   ├── FiltroPortugues.java      # Filtro para jogos em português
│   ├── SortingAlgorithms.java    # Implementação dos algoritmos de ordenação
│   └── ResultadoOrdenacao.java   # Classe para armazenar resultados
├── dados/
│   ├── games.csv                 # Arquivo original com os dados
│   ├── games_formated_release_data.csv  # Datas convertidas
│   ├── games_linux.csv           # Jogos com suporte a Linux
│   ├── games_portugues.csv       # Jogos com suporte a português
│   └── resultados/               # Pasta com os resultados das ordenações
└── README.txt                    # Este arquivo
```

## Como Executar

1. Certifique-se de que o arquivo `games.csv` está na pasta `dados/`
2. Compile os arquivos Java:
   ```
   javac *.java
   ```
3. Execute o programa:
   ```
   java Main
   ```

## Saída

O programa gera:
1. Arquivos CSV com os dados processados na pasta `dados/`
2. Uma tabela formatada mostrando:
   - Nome do algoritmo
   - Atributo sendo ordenado
   - Tipo de caso (Melhor, Médio, Pior)
   - Tempo de execução em milissegundos
   - Memória utilizada em KB
3. Arquivos CSV com os resultados ordenados na pasta `dados/resultados/`

## Formato dos Arquivos de Resultado

Os arquivos de resultado seguem o padrão:
`games_[atributo]_[algoritmo]_[caso].csv`

Exemplos:
- `games_releaseDate_SelectionSort_melhorCaso.csv`
- `games_price_QuickSort_medioCaso.csv`
- `games_achievements_CountingSort_piorCaso.csv`

REQUISITOS DO SISTEMA
--------------------
- Sistema Operacional: Windows, macOS ou Linux
- Java Development Kit (JDK) instalado
- Mínimo de 4GB de RAM disponível
- Aproximadamente 100MB de espaço em disco

SOLUÇÃO DE PROBLEMAS
-------------------
1. Erro "javac não é reconhecido como comando":
   - Verifique se o JDK está instalado
   - Verifique se a variável de ambiente JAVA_HOME está configurada
   - Verifique se o Java está no PATH do sistema
   - No Linux/macOS, você pode precisar usar 'sudo apt-get install default-jdk' ou 'brew install java'

2. Erro ao ler arquivo CSV:
   - Verifique se o arquivo games.csv existe na pasta dados
   - Verifique se o arquivo tem permissão de leitura
   - No Linux/macOS, você pode precisar ajustar as permissões com 'chmod'

3. Erro de memória:
   - Feche outros programas para liberar memória
   - Verifique se há espaço suficiente em disco
   - No Linux/macOS, você pode ajustar a memória Java com '-Xmx'

CONTATO
-------
Em caso de dúvidas ou problemas, consulte o relatório de desenvolvimento (RELATORIO_DEVELOPMENT.md) para mais detalhes sobre a implementação.

---

Última atualização: 6 de maio de 2025 