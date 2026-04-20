import java.util.*;

/**
 * Implementação do algoritmo DSatur (Degree of Saturation) para coloração de grafos.
 * O DSatur é uma heurística gulosa que prioriza vértices com maior grau de saturação
 * (número de cores distintas na vizinhança).
 */
public class GraphColoringDSatur {

    /**
     * Classe interna para armazenar informações de um vértice.
     */
    private static class NodeInfo {
        int sat;      // Grau de saturação
        int deg;      // Grau do vértice
        int vertex;   // Identificador do vértice

        NodeInfo(int sat, int deg, int vertex) {
            this.sat = sat;
            this.deg = deg;
            this.vertex = vertex;
        }
    }

    /**
     * Comparador para a fila de prioridade.
     * Prioriza: 1) maior grau de saturação, 2) maior grau, 3) maior índice do vértice.
     */
    private static class MaxSat implements Comparator<NodeInfo> {
        @Override
        public int compare(NodeInfo lhs, NodeInfo rhs) {
            if (lhs.sat != rhs.sat) {
                return Integer.compare(rhs.sat, lhs.sat);
            } else if (lhs.deg != rhs.deg) {
                return Integer.compare(rhs.deg, lhs.deg);
            } else {
                return Integer.compare(rhs.vertex, lhs.vertex);
            }
        }
    }

    /**
     * Classe para armazenar o resultado da coloração.
     */
    public static class DSaturColoringResult {
        public int[] colors;           // colors[v] = cor atribuída ao vértice v
        public int[] coloringOrder;    // ordem em que os vértices foram coloridos
        public int numColors;          // número total de cores utilizadas
    }

    /**
     * Executa o algoritmo DSatur para coloração de grafos não direcionados.
     *
     * @param G o grafo não direcionado a ser colorido
     * @return resultado contendo as cores, ordem de coloração e número de cores
     */
    public static DSaturColoringResult dsatur(Graph G) {
        int V = G.V();
        int[] colors = new int[V];           // cores dos vértices
        int[] coloringOrder = new int[V];    // ordem de coloração
        int colorIndex = 0;
        boolean[] colored = new boolean[V];  // vértices já coloridos
        
        // Inicializar cores como -1 (não colorido)
        Arrays.fill(colors, -1);

        // Estrutura para armazenar as cores adjacentes a cada vértice
        @SuppressWarnings("unchecked")
        Set<Integer>[] adjacentColors = new Set[V];
        for (int i = 0; i < V; i++) {
            adjacentColors[i] = new HashSet<>();
        }

        // Fila de prioridade ordenada por DSatur
        PriorityQueue<NodeInfo> pq = new PriorityQueue<>(new MaxSat());

        // Inicializar a fila com todos os vértices (DSatur = 0, grau = degree)
        for (int v = 0; v < V; v++) {
            int degree = G.degree(v);
            pq.add(new NodeInfo(0, degree, v));
        }

        // Algoritmo DSatur
        while (!pq.isEmpty()) {
            // Encontrar o primeiro vértice não colorido na fila
            NodeInfo node = null;
            while (!pq.isEmpty()) {
                NodeInfo temp = pq.poll();
                if (!colored[temp.vertex]) {
                    node = temp;
                    break;
                }
            }

            if (node == null) break;

            int u = node.vertex;
            colored[u] = true;
            coloringOrder[colorIndex++] = u;

            // Encontrar a menor cor disponível
            int color = 0;
            boolean[] usedColors = new boolean[V + 1];
            
            for (int neighbor : G.adj(u)) {
                if (colors[neighbor] != -1) {
                    usedColors[colors[neighbor]] = true;
                }
            }

            // Atribuir a menor cor não usada
            while (usedColors[color]) {
                color++;
            }
            colors[u] = color;

            // Atualizar as cores adjacentes dos vizinhos não coloridos
            for (int v : G.adj(u)) {
                if (!colored[v]) {
                    adjacentColors[v].add(color);
                }
            }

            // Recriar a fila com vértices não coloridos (DSatur atualizado)
            PriorityQueue<NodeInfo> newPq = new PriorityQueue<>(new MaxSat());
            while (!pq.isEmpty()) {
                NodeInfo temp = pq.poll();
                if (!colored[temp.vertex]) {
                    int dsat = adjacentColors[temp.vertex].size();
                    int degree = G.degree(temp.vertex);
                    newPq.add(new NodeInfo(dsat, degree, temp.vertex));
                }
            }
            pq = newPq;
        }

        // Calcular número total de cores utilizadas
        int maxColor = 0;
        for (int color : colors) {
            if (color > maxColor) {
                maxColor = color;
            }
        }

        // Preparar resultado
        DSaturColoringResult result = new DSaturColoringResult();
        result.colors = colors;
        result.coloringOrder = Arrays.copyOf(coloringOrder, colorIndex);
        result.numColors = maxColor + 1;

        return result;
    }

    /**
     * Método principal para testes locais.
     */
    public static void main(String[] args) {
        // Exemplo de teste simples
        Graph G1 = new Graph(5);
        G1.addEdge(0, 1);
        G1.addEdge(0, 2);
        G1.addEdge(1, 2);
        G1.addEdge(1, 3);
        G1.addEdge(2, 3);
        G1.addEdge(3, 4);

        System.out.println("Teste DSatur no grafo G1:");
        DSaturColoringResult resultado = dsatur(G1);
        
        for (int v = 0; v < G1.V(); v++) {
            System.out.println("Vertex " + v + " ---> Color " + resultado.colors[v]);
        }
        System.out.println("Total de cores: " + resultado.numColors);
    }
}
// This code is contributed by Akash Jha
