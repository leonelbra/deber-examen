package juego;

import java.util.Random;

public class Tablero {
    private final int filas = 10;
    private final int columnas = 10;
    private final int minas = 10;

    private Casilla[][] casillas;

    public Tablero() {
        casillas = new Casilla[filas][columnas];
        inicializarCasillas();
        colocarMinas();
        contarMinasAlrededor();
    }

    private void inicializarCasillas() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }

    private void colocarMinas() {
        Random rand = new Random();
        int minasColocadas = 0;
        while (minasColocadas < minas) {
            int fila = rand.nextInt(filas);
            int col = rand.nextInt(columnas);
            if (!casillas[fila][col].tieneMina()) {
                casillas[fila][col].colocarMina();
                minasColocadas++;
            }
        }
    }

    private void contarMinasAlrededor() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!casillas[i][j].tieneMina()) {
                    for (int[] dir : direcciones()) {
                        int ni = i + dir[0], nj = j + dir[1];
                        if (esValido(ni, nj) && casillas[ni][nj].tieneMina()) {
                            casillas[i][j].incrementarMinasAlrededor();
                        }
                    }
                }
            }
        }
    }

    private int[][] direcciones() {
        return new int[][]{
            {-1, -1}, {-1, 0}, {-1, 1},
            { 0, -1},          { 0, 1},
            { 1, -1}, { 1, 0}, { 1, 1}
        };
    }

    private boolean esValido(int fila, int col) {
        return fila >= 0 && fila < filas && col >= 0 && col < columnas;
    }

    public boolean descubrir(int fila, int col) {
        if (!esValido(fila, col) || casillas[fila][col].estaDescubierta()) {
            return true;
        }

        casillas[fila][col].descubrir();

        if (casillas[fila][col].tieneMina()) {
            return false; // Pierde el jugador
        }

        if (casillas[fila][col].getMinasAlrededor() == 0) {
            for (int[] dir : direcciones()) {
                int ni = fila + dir[0], nj = col + dir[1];
                if (esValido(ni, nj)) {
                    descubrir(ni, nj);
                }
            }
        }

        return true;
    }

    public void marcar(int fila, int col) {
        if (esValido(fila, col)) {
            casillas[fila][col].marcar();
        }
    }

    public void mostrarTablero() {
        System.out.print("  ");
        for (int j = 0; j < columnas; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        for (int i = 0; i < filas; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < columnas; j++) {
                System.out.print(casillas[i][j].mostrar() + " ");
            }
            System.out.println();
        }
    }

    public boolean juegoGanado() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!casillas[i][j].tieneMina() && !casillas[i][j].estaDescubierta()) {
                    return false;
                }
            }
        }
        return true;
    }
}
