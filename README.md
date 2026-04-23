# Trabalho Pratico 5 - Coloracao de Grafos com DSatur (Brasil)

Projeto da disciplina de Grafos (UNIFOR) para modelar o mapa politico do Brasil como um grafo nao direcionado e aplicar a heuristica DSatur para coloracao de vertices.

Link do video: https://youtu.be/K_EmePGqdvk 

## Visao Geral

Neste trabalho:

- cada vertice representa um estado;
- cada aresta representa uma fronteira terrestre;
- a coloracao e feita com o algoritmo DSatur;
- o programa valida automaticamente se a coloracao final e correta;
- uma imagem do grafo colorido e gerada em PNG.

O projeto possui duas execucoes:

- versao oficial com 26 estados (sem DF): dados/brasil.txt + Main;
- versao alternativa com 27 estados (com DF): dados/brasil_27.txt + Main27.

## Resultado Obtido (execucao real)

Compilacao e execucao foram testadas neste projeto:

- Main (26 estados): 4 cores utilizadas, coloracao valida, gera grafo_colorido.png;
- Main27 (27 estados): 4 cores utilizadas, coloracao valida, gera grafo_colorido_27.png.

## Estrutura do Projeto

```text
T5/
├── README.md
├── T5.md
├── executar.bat
├── executar27.bat
├── limpar.bat
├── dados/
│   ├── brasil.txt
│   ├── brasil_27.txt
│   └── mapeamento_estados.txt
├── bin/
├── lib/
└── src/
   ├── Main.java
   ├── Main27.java
   ├── GraphColoringDSatur.java
   ├── GraphColoringVisualizer.java
   ├── GraphColoringVisualizer27.java
   ├── Graph.java
   └── classes de apoio (Bag, Queue, Stack, StdDraw, In, etc.)
```

## Requisitos

- Java JDK 8+ instalado;
- terminal no Windows (scripts .bat prontos);
- opcional: adaptacao dos comandos para Linux/macOS.

## Como Executar

### Opcao 1 - Script pronto (26 estados)

```bat
executar.bat
```

Esse script:

- limpa compilacoes antigas;
- compila src/*.java em bin;
- executa Main com dados/brasil.txt;
- salva a imagem grafo_colorido.png.

### Opcao 2 - Script pronto (27 estados)

```bat
executar27.bat
```

Esse script executa Main27 com dados/brasil_27.txt e salva grafo_colorido_27.png.

### Opcao 3 - Execucao manual

```bat
javac -d bin -cp "bin;lib" src\*.java
java -cp "bin;lib" Main dados/brasil.txt
java -cp "bin;lib" Main27 dados/brasil_27.txt
```

## Formato dos Arquivos de Entrada

Padrao algs4 para grafo nao direcionado:

```text
V
E
v1 w1
v2 w2
...
```

Onde:

- V = numero de vertices;
- E = numero de arestas;
- cada linha v w representa uma fronteira terrestre entre dois estados.

Arquivos usados no projeto:

- dados/brasil.txt: 26 vertices e 49 arestas;
- dados/brasil_27.txt: 27 vertices e 51 arestas.

## O Que o Programa Exibe

Ao executar, o programa mostra:

- lista de adjacencia completa do grafo;
- ordem de coloracao dos vertices;
- cor atribuida a cada estado;
- total de cores utilizadas;
- validacao da coloracao (garante cor[u] != cor[v] para cada aresta).

## DSatur (Resumo Tecnico)

O DSatur e uma heuristica gulosa de coloracao. A cada iteracao:

1. escolhe o vertice nao colorido com maior grau de saturacao (numero de cores distintas na vizinhanca);
2. em empate, escolhe o de maior grau no grafo;
3. atribui a menor cor disponivel que nao conflita com os vizinhos.

No codigo, a implementacao principal esta em src/GraphColoringDSatur.java.

## Mapeamento dos Estados

O mapeamento de indices para siglas esta documentado em dados/mapeamento_estados.txt.

No codigo:

- Main usa 26 estados (sem DF);
- Main27 usa 27 estados (com DF no indice 6).

## Arquivos de Saida Gerados

- grafo_colorido.png (execucao Main);
- grafo_colorido_27.png (execucao Main27).

## Limpeza do Projeto

Para remover classes e arquivos gerados:

```bat
limpar.bat
```

## Checklist de Entrega

- modelagem correta das fronteiras terrestres;
- implementacao do DSatur;
- saida textual clara com validacao;
- repositorio com instrucoes de execucao;
- link do video no README.



## Autor

- Disciplina: Grafos
- Instituicao: Universidade de Fortaleza (UNIFOR)
- Trabalho: AV2 - Atividade 5
