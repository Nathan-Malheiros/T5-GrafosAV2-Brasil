import java.io.File;
import java.util.Scanner;

public class Main {
    // Mapeamento obrigatório dos estados brasileiros (ordem alfabética por sigla)
    static final String[] ESTADOS = {
            "AC", "AL", "AM", "AP", "BA", "CE", "ES", "GO", "MA", "MG",
            "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO",
            "RR", "RS", "SC", "SE", "SP", "TO"
    };

    // Nomes das cores para exibição
    static final String[] NOMES_CORES = {
            "Vermelho", "Azul", "Verde", "Amarelo", "Ciano", "Magenta"
    };

    public static void main(String[] args) {
        // Caminho do arquivo: recebe por linha de comando ou usa padrão
        String caminhoArquivo = (args.length > 0) ? args[0] : "dados/brasil.txt";

        try {
            File file = new File(caminhoArquivo);
            if (!file.exists()) {
                System.out.println("Erro: Arquivo " + caminhoArquivo + " nao encontrado.");
                return;
            }

            Scanner sc = new Scanner(file);
            int V = sc.nextInt();
            int E = sc.nextInt();

            // 1. CONSTRUÇÃO DO GRAFO NÃO DIRECIONADO (lista de adjacência)
            Graph G = new Graph(V);

            for (int i = 0; i < E; i++) {
                int v = sc.nextInt();
                int w = sc.nextInt();
                G.addEdge(v, w);
            }
            sc.close();

            // (a) EXIBIR A LISTA DE ADJACÊNCIA DO GRAFO com siglas dos estados
            System.out.println("=== Lista de Adjacencia do Mapa Politico do Brasil ===");
            System.out.println(V + " vertices, " + G.E() + " arestas");
            System.out.println();
            exibirListaAdjacencia(G);

            // (b) + (c) EXECUTAR O ALGORITMO DSATUR
            System.out.println("\n=== Executando Algoritmo DSatur ===");
            GraphColoringDSatur.DSaturColoringResult resultado = GraphColoringDSatur.dsatur(G);

            // (b) Ordem de coloração dos vértices
            System.out.println("\nOrdem de coloracao dos vertices:");
            for (int i = 0; i < resultado.coloringOrder.length; i++) {
                int estado = resultado.coloringOrder[i];
                int cor = resultado.colors[estado];
                System.out.printf("  %2d. %s (vertice %2d) -> Cor %d (%s)%n",
                        i + 1, ESTADOS[estado], estado, cor, nomeCor(cor));
            }

            // (c) Cor atribuída a cada estado (com sigla)
            System.out.println("\nCor atribuida a cada estado:");
            for (int v = 0; v < V; v++) {
                System.out.printf("  %s (vertice %2d): Cor %d (%s)%n",
                        ESTADOS[v], v, resultado.colors[v], nomeCor(resultado.colors[v]));
            }

            // (d) Total de cores utilizadas
            System.out.printf("%nTotal de cores utilizadas: %d%n", resultado.numColors);

            // (e) Verificação automática da coloração
            System.out.println("\n=== Validacao da Coloracao ===");
            boolean valida = validarColoracao(G, resultado.colors);
            if (valida) {
                System.out.println("Coloracao valida: para toda aresta (u, v), cor[u] != cor[v].");
            } else {
                System.out.println("Coloracao INVALIDA!");
            }

            // GERAR IMAGEM DO GRAFO COLORIDO (salva como grafo_colorido.png)
            System.out.println("\n=== Gerando imagem do grafo colorido ===");
            GraphColoringVisualizer.visualizar(G, resultado.colors);
            StdDraw.save("grafo_colorido.png");
            System.out.println("Imagem salva como: grafo_colorido.png");

        } catch (Exception e) {
            System.err.println("Erro ao processar o arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Exibe a lista de adjacência do grafo com as siglas dos estados.
     */
    static void exibirListaAdjacencia(Graph G) {
        int V = G.V();
        for (int v = 0; v < V; v++) {
            System.out.printf("  %2d (%s): ", v, ESTADOS[v]);
            boolean primeiro = true;
            for (int w : G.adj(v)) {
                if (!primeiro)
                    System.out.print(", ");
                System.out.printf("%s(%d)", ESTADOS[w], w);
                primeiro = false;
            }
            System.out.println();
        }
    }

    /**
     * Retorna o nome da cor a partir do índice.
     */
    static String nomeCor(int cor) {
        if (cor >= 0 && cor < NOMES_CORES.length) {
            return NOMES_CORES[cor];
        }
        return "Cor" + cor;
    }

    /**
     * Valida se a coloração é válida para o grafo.
     * Para toda aresta (u, v), verifica se cor[u] != cor[v].
     */
    static boolean validarColoracao(Graph G, int[] colors) {
        int V = G.V();
        boolean valida = true;
        for (int v = 0; v < V; v++) {
            for (int w : G.adj(v)) {
                if (colors[v] == colors[w]) {
                    System.out.printf("  Erro: %s (vertice %d) e %s (vertice %d) possuem a mesma cor %d!%n",
                            ESTADOS[v], v, ESTADOS[w], w, colors[v]);
                    valida = false;
                }
            }
        }
        return valida;
    }
}