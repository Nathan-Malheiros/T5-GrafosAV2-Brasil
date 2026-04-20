import java.awt.Color;

/**
 * Visualizacao do grafo colorido do Brasil com 27 estados (incluindo DF).
 */
public class GraphColoringVisualizer27 {

    private static final String[] ESTADOS = {
            "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG",
            "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO",
            "RR", "RS", "SC", "SE", "SP", "TO"
    };

    // Coordenadas baseadas nas posicoes geograficas reais dos estados
    private static final double[][] COORDENADAS = {
            { 1.1, 8.2 }, // 0: AC
            { 12.4, 7.8 }, // 1: AL
            { 2.9, 10.1 }, // 2: AM
            { 7.3, 11.8 }, // 3: AP
            { 10.8, 6.8 }, // 4: BA
            { 11.4, 9.8 }, // 5: CE
            { 8.5, 5.4 }, // 6: DF (dentro de GO, levemente deslocado)
            { 10.9, 4.4 }, // 7: ES
            { 8.0, 5.8 }, // 8: GO
            { 9.4, 9.8 }, // 9: MA
            { 9.8, 4.8 }, // 10: MG
            { 6.3, 4.0 }, // 11: MS
            { 5.8, 7.0 }, // 12: MT
            { 7.0, 10.2 }, // 13: PA
            { 12.8, 9.2 }, // 14: PB
            { 12.4, 8.5 }, // 15: PE
            { 10.2, 8.8 }, // 16: PI
            { 7.3, 2.6 }, // 17: PR
            { 10.1, 3.4 }, // 18: RJ
            { 12.8, 9.8 }, // 19: RN
            { 3.5, 7.4 }, // 20: RO
            { 4.2, 12.0 }, // 21: RR
            { 6.8, 1.0 }, // 22: RS
            { 7.8, 1.8 }, // 23: SC
            { 11.6, 7.2 }, // 24: SE
            { 8.1, 3.5 }, // 25: SP
            { 8.4, 7.8 } // 26: TO
    };

    private static final Color[] CORES_VISUALIZACAO = {
            new Color(231, 76, 60), // Vermelho
            new Color(52, 152, 219), // Azul
            new Color(46, 204, 113), // Verde
            new Color(241, 196, 15), // Amarelo
            new Color(26, 188, 156), // Ciano
            new Color(155, 89, 182) // Magenta
    };

    private static final String[] NOMES_CORES = {
            "Vermelho", "Azul", "Verde", "Amarelo", "Ciano", "Magenta"
    };

    public static void visualizar(Graph G, int[] colors) {
        int V = G.V();

        StdDraw.setCanvasSize(1100, 1000);
        StdDraw.setXscale(-0.8, 13.5);
        StdDraw.setYscale(-0.8, 13.2);
        StdDraw.clear();

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont();
        StdDraw.text(6.5, 12.8, "Coloracao de Grafos - Mapa do Brasil 27 estados (DSatur)");

        // Arestas
        StdDraw.setPenColor(new Color(180, 180, 180));
        StdDraw.setPenRadius(0.002);
        for (int v = 0; v < V; v++) {
            for (int w : G.adj(v)) {
                if (v < w) {
                    StdDraw.line(COORDENADAS[v][0], COORDENADAS[v][1],
                            COORDENADAS[w][0], COORDENADAS[w][1]);
                }
            }
        }

        // Vertices
        double raio = 0.22;
        for (int v = 0; v < V; v++) {
            double x = COORDENADAS[v][0];
            double y = COORDENADAS[v][1];
            int corIndex = colors[v] % CORES_VISUALIZACAO.length;

            StdDraw.setPenColor(CORES_VISUALIZACAO[corIndex]);
            StdDraw.filledCircle(x, y, raio);

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.004);
            StdDraw.circle(x, y, raio);

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont();
            StdDraw.text(x, y, ESTADOS[v]);
        }

        // Legenda
        int numCoresUsadas = 0;
        for (int c : colors) {
            if (c + 1 > numCoresUsadas)
                numCoresUsadas = c + 1;
        }
        double legendaX = 0.5;
        double legendaY = 2.0;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont();
        StdDraw.text(legendaX + 0.5, legendaY + 0.5, "Legenda:");
        for (int c = 0; c < numCoresUsadas; c++) {
            double ly = legendaY - c * 0.5;
            int ci = c % CORES_VISUALIZACAO.length;
            StdDraw.setPenColor(CORES_VISUALIZACAO[ci]);
            StdDraw.filledCircle(legendaX, ly, 0.15);
            StdDraw.setPenColor(StdDraw.BLACK);
            String nome = (c < NOMES_CORES.length) ? NOMES_CORES[c] : "Cor " + c;
            StdDraw.textLeft(legendaX + 0.3, ly, "Cor " + c + " (" + nome + ")");
        }

        StdDraw.show();
    }
}
