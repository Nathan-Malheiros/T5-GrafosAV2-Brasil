import java.io.File;
import java.util.Scanner;

/**
 * Versao com 27 estados (incluindo DF).
 * Rode separadamente com: java -cp bin Main27 "dados/brasil_27.txt"
 */
public class Main27 {
    // 27 estados em ordem alfabetica (DF = indice 6)
    static final String[] ESTADOS = {
            "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG",
            "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO",
            "RR", "RS", "SC", "SE", "SP", "TO"
    };

    static final String[] NOMES_CORES = {
            "Vermelho", "Azul", "Verde", "Amarelo", "Ciano", "Magenta"
    };

    public static void main(String[] args) {
        String caminhoArquivo = (args.length > 0) ? args[0] : "dados/brasil_27.txt";

        try {
            File file = new File(caminhoArquivo);
            if (!file.exists()) {
                System.out.println("Erro: Arquivo " + caminhoArquivo + " nao encontrado.");
                return;
            }

            Scanner sc = new Scanner(file);
            int V = sc.nextInt();
            int E = sc.nextInt();

            Graph G = new Graph(V);

            for (int i = 0; i < E; i++) {
                int v = sc.nextInt();
                int w = sc.nextInt();
                G.addEdge(v, w);
            }
            sc.close();

            System.out.println("=== Lista de Adjacencia do Mapa Politico do Brasil (27 estados) ===");
            System.out.println(V + " vertices, " + G.E() + " arestas");
            System.out.println();
            exibirListaAdjacencia(G);

            System.out.println("\n=== Executando Algoritmo DSatur ===");
            GraphColoringDSatur.DSaturColoringResult resultado = GraphColoringDSatur.dsatur(G);

            System.out.println("\nOrdem de coloracao dos vertices:");
            for (int i = 0; i < resultado.coloringOrder.length; i++) {
                int estado = resultado.coloringOrder[i];
                int cor = resultado.colors[estado];
                System.out.printf("  %2d. %s (vertice %2d) -> Cor %d (%s)%n",
                        i + 1, ESTADOS[estado], estado, cor, nomeCor(cor));
            }

            System.out.println("\nCor atribuida a cada estado:");
            for (int v = 0; v < V; v++) {
                System.out.printf("  %s (vertice %2d): Cor %d (%s)%n",
                        ESTADOS[v], v, resultado.colors[v], nomeCor(resultado.colors[v]));
            }

            System.out.printf("%nTotal de cores utilizadas: %d%n", resultado.numColors);

            System.out.println("\n=== Validacao da Coloracao ===");
            boolean valida = validarColoracao(G, resultado.colors);
            if (valida) {
                System.out.println("Coloracao valida: para toda aresta (u, v), cor[u] != cor[v].");
            } else {
                System.out.println("Coloracao INVALIDA!");
            }

            System.out.println("\n=== Gerando imagem do grafo colorido ===");
            GraphColoringVisualizer27.visualizar(G, resultado.colors);
            StdDraw.save("grafo_colorido_27.png");
            System.out.println("Imagem salva como: grafo_colorido_27.png");

        } catch (Exception e) {
            System.err.println("Erro ao processar o arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

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

    static String nomeCor(int cor) {
        if (cor >= 0 && cor < NOMES_CORES.length) {
            return NOMES_CORES[cor];
        }
        return "Cor" + cor;
    }

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
